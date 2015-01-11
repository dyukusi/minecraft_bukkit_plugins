package jp.mydns.dyukusi.regenerationpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import jp.mydns.dyukusi.regenerationpoint.command.SetRegeneArea;
import jp.mydns.dyukusi.regenerationpoint.regeneinfo.RegeneInfo;
import jp.mydns.dyukusi.regenerationpoint.task.Regeneration;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class RegenerationPoint extends JavaPlugin {

	LinkedList<RegeneInfo> regene_list;
	final String customarea_path = getDataFolder().getAbsolutePath() + "/regeneinfo.bin";

	// position
	private Location first;
	private Location second;

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
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml").exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		// get custom area info from serialized file.
		if (new File(customarea_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(new FileInputStream(customarea_path));
				this.regene_list = (LinkedList<RegeneInfo>) objinput.readObject();
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
			getLogger().info("regeneinfo.bin was not found. Creating new data...");
			this.regene_list = new LinkedList<RegeneInfo>();
		}

		// run task
		new Regeneration(this, regene_list).runTaskTimerAsynchronously(this, 0, 10);

		// register command
		this.getCommand("rp").setExecutor(new SetRegeneArea(this));
		
	}

	@Override
	public void onDisable() {
		this.getLogger().info(ChatColor.GOLD + " Saving Custom Area information...");
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(new FileOutputStream(this.customarea_path));
			objoutput.writeObject(this.regene_list);
			objoutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getLogger().info(ChatColor.GOLD + " Saving Custom Area information have been completed!");
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

	public void create_regene_area(Location a, Location b) {
		this.regene_list.add(new RegeneInfo(a, b));
	}

	public Location get_first_position() {
		return this.first;
	}

	public Location get_second_position() {
		return this.second;
	}

}
