package jp.mydns.dyukusi.effectarea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import jp.mydns.dyukusi.effectarea.command.BasicCommand;
import jp.mydns.dyukusi.effectarea.listener.MonsterSpawn;
import jp.mydns.dyukusi.effectarea.regeneinfo.EffectAreaInfo;
import jp.mydns.dyukusi.effectarea.task.Effecting_task;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.avaje.ebean.enhance.agent.LocalFieldVisitor;

public class EffectArea extends JavaPlugin {

	LinkedHashMap<String, EffectAreaInfo> effect_area_map;
	// LinkedList<EffectAreaInfo> effect_area_list;
	final String customarea_path = getDataFolder().getAbsolutePath()
			+ "/effectarea.bin";

	// position
	private Location first;
	private Location second;

	@SuppressWarnings("unchecked")
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
		// config
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		// get effect area info from serialized file.
		if (new File(customarea_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(
						new FileInputStream(customarea_path));
				this.effect_area_map = (LinkedHashMap<String, EffectAreaInfo>) (objinput
						.readObject());
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
			this.effect_area_map = new LinkedHashMap<String, EffectAreaInfo>();
		}

		// run task
		new Effecting_task(this, effect_area_map).runTaskTimer(this, 0, 20);

		// register command
		this.getCommand("ea").setExecutor(new BasicCommand(this));

		// register listener
		this.getServer().getPluginManager()
				.registerEvents(new MonsterSpawn(this), this);

	}

	@Override
	public void onDisable() {
		this.getLogger().info(
				ChatColor.GOLD + " Saving Custom Area information...");
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(this.customarea_path));
			objoutput.writeObject(this.effect_area_map);
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

	public void set_position(Location location, boolean isSecondPos) {
		// first pos
		if (!isSecondPos) {
			this.first = location;
		}
		// second pos
		else {
			this.second = location;
		}
	}

	public void add_effect_area(String area_name, Location a, Location b) {
		this.effect_area_map
				.put(area_name, new EffectAreaInfo(area_name, a, b));
		this.getServer().broadcastMessage("Add new effect area completed!");
	}

	public void remove_effect_area(String area_name) {
		this.effect_area_map.remove(area_name);
	}

	public Location get_first_position() {
		return this.first;
	}

	public Location get_second_position() {
		return this.second;
	}

	public boolean isEffect_area(String area_name) {
		if (this.effect_area_map.containsKey(area_name))
			return true;
		else
			return false;
	}

	public EffectAreaInfo get_effect_area_info(String area_name) {
		return this.effect_area_map.get(area_name);
	}

	public EffectAreaInfo is_in_effect_area(Location loc) {

		for (EffectAreaInfo info : effect_area_map.values()) {

			if (info.is_in_area(loc)) {
				return info;
			}

		}

		return null;

	}

}
