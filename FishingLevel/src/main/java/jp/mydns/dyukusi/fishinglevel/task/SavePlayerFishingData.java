package jp.mydns.dyukusi.fishinglevel.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jp.mydns.dyukusi.fishinglevel.FishingLevel;
import jp.mydns.dyukusi.fishinglevel.playerdata.PlayerFishingLevelData;

import org.bukkit.scheduler.BukkitRunnable;

public class SavePlayerFishingData extends BukkitRunnable {

	FishingLevel plugin;
	String path;

	public SavePlayerFishingData(FishingLevel fishingLevel, String path) {
		this.plugin = fishingLevel;
		this.path = path;
	}

	public void run() {
		plugin.getLogger().info("Saving Player's FishingLevel data...");

		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (PlayerFishingLevelData data : plugin.get_playerfishleveldata()
				.values()) {
			pw.println(data.get_player_name() + "," + data.get_level() + ","
					+ data.get_exp());
		}

		pw.close();

		plugin.getLogger().info(
				"Saving Player's FishingLevel data is correctly completed!!");
	}

}
