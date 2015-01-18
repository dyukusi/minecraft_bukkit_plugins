package jp.mydns.dyukusi.areanotificator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import jp.mydns.dyukusi.areanotificator.command.CustomArea;
import jp.mydns.dyukusi.areanotificator.custominfo.*;
import jp.mydns.dyukusi.areanotificator.listener.AutomaticAreaDecider;
import jp.mydns.dyukusi.areanotificator.listener.PlayerLoginOut;
import jp.mydns.dyukusi.areanotificator.task.Notificator;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class AreaNotificator extends JavaPlugin {

	private String prefix = ChatColor.LIGHT_PURPLE + "[" + this.getName() + "]";

	private LinkedHashMap<String, CustomAreaInfo> customarea_map;
	private LinkedList<String> ignore_area;

	final String customarea_path = getDataFolder().getAbsolutePath()
			+ "/customarea.bin";

	@Override
	public void onEnable() {

		// mcstats
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.RED + "Failed to submit the stats");
			// Failed to submit the stats
		}

		// config test
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		// ignore notificate
		ignore_area = new LinkedList<String>();
		ignore_area.add(Biome.RIVER.name());
		

		// get custom area info from serialized file.
		if (new File(customarea_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(
						new FileInputStream(customarea_path));
				this.customarea_map = (LinkedHashMap<String, CustomAreaInfo>) objinput
						.readObject();
				objinput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		// no save data
		else {
			getLogger().info(
					"characterlevel.bin was not found. Creating new data...");
			this.customarea_map = new LinkedHashMap<String, CustomAreaInfo>();
		}

		// register listner
		this.getServer().getPluginManager()
				.registerEvents(new PlayerLoginOut(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new AutomaticAreaDecider(this), this);

		// register command
		this.getCommand("an").setExecutor(new CustomArea(this));

		int i = 0;
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.run_notificator(player, i, false);
			i++;
		}
	}

	@Override
	public void onDisable() {
		this.getLogger().info(
				ChatColor.GOLD + " Saving Custom Area information...");
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(this.customarea_path));
			objoutput.writeObject(this.customarea_map);
			objoutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getLogger()
				.info(ChatColor.GOLD
						+ " Saving Custom Area information have been completed!");
	}

	public void run_notificator(Player player, long offset, boolean first_notify) {
		player.setMetadata("area", new FixedMetadataValue(this, null));

		if (first_notify) {
			new Notificator(this, player).runTaskTimerAsynchronously(this,
					120L, 60L);
		} else {
			new Notificator(this, player).runTaskTimerAsynchronously(this,
					(long) (offset * 13), 60L);
		}

	}

	public String get_current_area_name(Entity player) {
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();
		CustomAreaInfo custom_area = null;

		for (Entry<String, CustomAreaInfo> ent : this.customarea_map.entrySet()) {
			CustomAreaInfo area = ent.getValue();
			if (area.is_in_area(player)) {
				custom_area = area;
			}
		}

		if (custom_area == null) {
			return player.getWorld().getBiome(x, z).name();
		}
		// in custom area
		else {
			return custom_area.get_area_name();
		}
	}

	public boolean isSame_area_with_last_time(Player player) {
		List<MetadataValue> values = player.getMetadata("area");
		String before_area_name = null;

		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(this.getDescription().getName())) {
				before_area_name = (String) value.value();
				break;
			}
		}

		String current_biome_name = this.get_current_area_name(player);

		if(ignore_area.contains(current_biome_name)){		
			return true;
		}
		
		// mix
		if (current_biome_name.equals(Biome.STONE_BEACH.name())) {
			current_biome_name = Biome.BEACH.name();
		} else if (current_biome_name.equals(Biome.EXTREME_HILLS_PLUS.name())) {
			current_biome_name = Biome.EXTREME_HILLS.name();
		}

		if (current_biome_name.equals(before_area_name)) {
			return true;
		} else {
			return false;
		}
	}

	public String get_prefix() {
		return this.prefix;
	}

	public void add_new_cutomarea(CustomAreaInfo info) {
		this.customarea_map.put(info.get_area_name(), info);
	}

	public CustomAreaInfo get_customarea_info(String area_name) {
		return this.customarea_map.get(area_name);
	}

	public boolean isCustomArea(String areaname) {
		if (this.customarea_map.containsKey(areaname))
			return true;
		else
			return false;
	}

	public void remove_custom_area(String area_name) {
		this.customarea_map.remove(area_name);
	}

	public void create_new_area(Location location, int radius, String title,
			String subtitle) {
		int x = location.getBlockX();
		int z = location.getBlockZ();

		Location first = new Location(location.getWorld(), x + radius, 0, z
				+ radius);
		Location second = new Location(location.getWorld(), x - radius, 0, z
				- radius);

		CustomAreaInfo info = new CustomAreaInfo(title, subtitle, first, second);

		// display as subtitle. not creator name
		info.as_subtitle();

		this.add_new_cutomarea(info);
	}

}
