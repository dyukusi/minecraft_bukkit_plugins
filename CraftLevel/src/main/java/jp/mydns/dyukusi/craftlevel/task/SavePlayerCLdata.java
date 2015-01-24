package jp.mydns.dyukusi.craftlevel.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class SavePlayerCLdata extends BukkitRunnable {
	CraftLevel plugin;
	HashMap<UUID, PlayerCraftLevelData> player_crafting_level;
	String path;

	public SavePlayerCLdata(CraftLevel craftLevel, HashMap<UUID, PlayerCraftLevelData> pcl, String path) {
		this.plugin = craftLevel;
		this.player_crafting_level = pcl;
		this.path = path;
	}

	@Override
	public void run() {
		
		plugin.getLogger().info("Saving Player's CraftLevel data...");

		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		pw.println("# PlayerName, CraftLevel, CraftEXP");

		for (Entry<UUID, PlayerCraftLevelData> ent : this.player_crafting_level
				.entrySet()) {
			PlayerCraftLevelData pinfo = ent.getValue();

			pw.println(pinfo.get_player_name() + "," + pinfo.get_level() + ","
					+ pinfo.get_exp());
		}

		pw.close();
		

		plugin.getLogger().info("Saving Player's CraftLevel data is correctly completed!!");
	}

}
