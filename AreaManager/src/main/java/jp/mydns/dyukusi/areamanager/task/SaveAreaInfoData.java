package jp.mydns.dyukusi.areamanager.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import jp.mydns.dyukusi.areamanager.AreaManager;
import jp.mydns.dyukusi.areamanager.areainfo.AreaInformation;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveAreaInfoData extends BukkitRunnable {

	AreaManager plugin;
	String path;

	public SaveAreaInfoData(AreaManager areaManager, String path) {
		this.plugin = areaManager;
		this.path = path;
	}

	public void run() {

		plugin.getLogger().info("Saving AreaManager data...");

		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		pw.println("# World, AreaName, CustomAreaName, Owner, Price, location1, location2, IgnoreY, CanBuy");

		// save area information
		for (Entry<String, AreaInformation> ent : plugin.get_area_entrySet()) {
			AreaInformation info = ent.getValue();

			pw.println(info.get_world_name() + "," + info.get_area_name() + ","
					+ info.get_custom_area_name() + "," + info.get_owner_name()
					+ "," + info.get_price() + ","
					+ info.get_first_position()[0] + ","
					+ info.get_first_position()[1] + ","
					+ info.get_first_position()[2] + ","
					+ info.get_second_position()[0] + ","
					+ info.get_second_position()[1] + ","
					+ info.get_second_position()[2] + ","
					+ Boolean.toString(info.get_ignore_y()) + ","
					+ Boolean.toString(info.get_can_buy()) + ","
					+ info.get_last_played_time());

		}

		pw.close();

		plugin.getLogger().info(
				"Saving Player's CraftLevel data has been completed!");
	}
}
