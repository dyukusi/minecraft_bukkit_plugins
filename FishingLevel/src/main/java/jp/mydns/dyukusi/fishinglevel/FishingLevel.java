package jp.mydns.dyukusi.fishinglevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import jp.mydns.dyukusi.fishinglevel.command.BaisicCommands;
import jp.mydns.dyukusi.fishinglevel.listener.Fishing;
import jp.mydns.dyukusi.fishinglevel.listener.PlayerJoin;
import jp.mydns.dyukusi.fishinglevel.playerdata.PlayerFishingLevelData;
import jp.mydns.dyukusi.fishinglevel.task.SavePlayerFishingData;
import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FishingLevel extends JavaPlugin {

	SeasonalFood seasonalfood;
	HashMap<String, PlayerFishingLevelData> playerfishdata;
	int max_level;
	int next_level_exp[];

	String playerfishinglevel_path = this.getDataFolder().getAbsolutePath()
			+ "/fishinglevel.txt";

	@Override
	public void onEnable() {


		// import SeasonalFood plugin
		if ((this.seasonalfood = (SeasonalFood) this.getServer()
				.getPluginManager().getPlugin("SeasonalFood")) == null) {

			this.getLogger().info("Can not load SeasonalFood");
			onDisable();
		}

		// config file
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		// max_level
		this.max_level = this.getConfig().getInt("max_level");

		// next_level
		List<String> next_level_list = getConfig().getStringList("next_level");
		this.next_level_exp = new int[next_level_list.size() + 1];
		int level, require_exp;
		String array[];
		for (String str : getConfig().getStringList("next_level")) {
			array = str.split(",");
			level = Integer.parseInt(array[0]);
			require_exp = Integer.parseInt(array[1]);
			next_level_exp[level] = require_exp;
		}

		// initialize
		this.playerfishdata = new HashMap<String, PlayerFishingLevelData>();

		// load player fishing level
		if (!new File(this.getDataFolder().getAbsolutePath()
				+ "/fishinglevel.txt").exists()) {
			getLogger().info("fishinglevel.txt doesn't exist. creating...");

			try {
				new File(this.getDataFolder().getAbsolutePath()
						+ "/fishinglevel.txt").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			Scanner sc = null;
			try {
				sc = new Scanner(new File(playerfishinglevel_path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (sc != null) {
				while (sc.hasNextLine()) {
					String[] data = sc.nextLine().split(",");
					String name = data[0];
					int fishing_level = Integer.parseInt(data[1]);
					int fishing_exp = Integer.parseInt(data[2]);

					this.playerfishdata.put(name, new PlayerFishingLevelData(
							name, fishing_level, fishing_exp));
				}
			}

		}

		// register listener
		this.getServer().getPluginManager()
				.registerEvents(new Fishing(this, seasonalfood), this);
		this.getServer().getPluginManager()
				.registerEvents(new PlayerJoin(this), this);

		this.getLogger().info(
				"current season : " + seasonalfood.get_current_season());

		// register commands
		this.getCommand("fl").setExecutor(new BaisicCommands(this));

	}

	@Override
	public void onDisable() {
		new SavePlayerFishingData(this, this.playerfishinglevel_path).run();
	}

	public HashMap<String, PlayerFishingLevelData> get_playerfishleveldata() {
		return this.playerfishdata;
	}

	public void register_new_player(Player player) {
		this.playerfishdata.put(player.getName(), new PlayerFishingLevelData(
				player.getName(), 1, 0));
	}

	public String get_playerfishingdata_path() {
		return this.playerfishinglevel_path;
	}

	public int get_required_exp_from_level(int level) {
		return this.next_level_exp[level];
	}

}
