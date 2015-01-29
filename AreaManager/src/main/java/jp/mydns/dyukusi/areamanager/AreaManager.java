package jp.mydns.dyukusi.areamanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import jp.mydns.dyukusi.areamanager.areainfo.AreaInformation;
import jp.mydns.dyukusi.areamanager.command.BasicCommands;
import jp.mydns.dyukusi.areamanager.task.AreaInfoProvidor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class AreaManager extends JavaPlugin {
	private Economy economy = null;
	private WorldGuardPlugin wg = null;
	private HashMap<String, AreaInformation> area_info;
	final String areainfo_path = getDataFolder().getAbsolutePath()
			+ "/areainfo.txt";
	private String prefix = ChatColor.LIGHT_PURPLE + "[" + this.getName() + "]";

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

		// config
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("creating config.yml file...");
			this.saveDefaultConfig();
		}

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// import WorldGuard
		Plugin p = getServer().getPluginManager().getPlugin("WorldGuard");
		// WorldGuard may not be loaded
		if (p == null || !(p instanceof WorldGuardPlugin)) {
			getLogger()
					.info("WorldGuard was not found ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		} else {
			this.wg = (WorldGuardPlugin) p;
		}

		// wg.getRegionManager(getServer().getWorld("world")).addRegion();
		// ProtectedCuboidRegion test_rg = new ProtectedCuboidRegion("test-rg",
		// new BlockVe, pt2)

		// read save data
		this.area_info = new HashMap<String, AreaInformation>();

		// register command
		this.getCommand("am").setExecutor(new BasicCommands(this));

		// run area info providor
		int i = 0;
		for (Player player : this.getServer().getOnlinePlayers()) {
			new AreaInfoProvidor(this, player).runTaskTimer(this, i, 13);
			i++;
		}

	}

	@Override
	public void onDisable() {

	}

	boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			this.economy = economyProvider.getProvider();
		}
		return (economy != null);
	}

	static public BlockVector convertToSk89qBV(Location location) {
		return new com.sk89q.worldedit.BlockVector(location.getX(),
				location.getY(), location.getZ());
	}

	public boolean isSame_area_with_last_time(Player player) {

		if (player.hasMetadata("am_area")) {
			List<MetadataValue> values = player.getMetadata("am_area");
			String before_area_name = null;

			for (MetadataValue value : values) {
				if (value.getOwningPlugin().getDescription().getName()
						.equals(this.getDescription().getName())) {
					before_area_name = (String) value.value();
					break;
				}
			}

			String current_area_name = this.get_current_area_name(player);

			if (current_area_name.equals(before_area_name)) {
				return true;
			} else {
				return false;
			}
		} else {
			player.setMetadata("am_area", new FixedMetadataValue(this,
					get_current_area_name(player)));
			return true;
		}
	}

	public String get_prefix() {
		return this.prefix;
	}

	public String get_current_area_name(Entity player) {
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();
		AreaInformation custom_area = null;

		for (Entry<String, AreaInformation> ent : this.area_info.entrySet()) {
			AreaInformation area = ent.getValue();
			if (area.is_in_area(player)) {
				custom_area = area;
			}
		}

		if (custom_area == null) {
			return "null";
		}
		// in custom area
		else {
			return custom_area.get_area_name();
		}
	}

	public boolean isRegisteredArea(String areaname) {
		if (this.area_info.containsKey(areaname))
			return true;
		else
			return false;
	}

	public void remove_custom_area(String area_name) {
		this.area_info.remove(area_name);
	}

	public Set<Entry<String, AreaInformation>> get_area_entrySet() {
		return this.area_info.entrySet();
	}

	public void add_new_area(AreaInformation info) {
		this.area_info.put(info.get_area_name(), info);
	}

	public AreaInformation get_area_info(String area_name) {
		if (this.area_info.containsKey(area_name)) {
			return this.area_info.get(area_name);
		} else {
			return null;
		}
	}

	public WorldGuardPlugin get_wg() {
		return this.wg;
	}
	
	public Economy get_economy(){
		return this.economy;
	}

}
