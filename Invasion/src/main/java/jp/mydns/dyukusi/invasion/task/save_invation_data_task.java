package jp.mydns.dyukusi.invasion.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class save_invation_data_task extends BukkitRunnable {

	Invasion plugin;
	InvasionData d;
	String save_path;

	public save_invation_data_task(Invasion plugin, InvasionData invd,
			String save_path) {
		this.plugin = plugin;
		this.d = invd;
		this.save_path = save_path;
	}

	@Override
	public void run() {
		plugin.getLogger().info("Saving Invasion data...");

		File file = new File(this.save_path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// pw.println("# PlayerName, CraftLevel, CraftEXP");

		// save data
		pw.println(d.get_invasion_name() + ":");
		pw.println();

		pw.println("#How long time does the invation continue.");
		pw.println("  " + "duration: " + d.get_duration_sec());
		pw.println();

		pw.println("  " + "destination:");
		for (Location dest : d.get_dest_list()) {
			pw.println("    - " + dest.getX() + "," + dest.getY() + ","
					+ dest.getZ());

		}
		pw.println();

		pw.println("  " + "spawn_check_location:");
		for (Location check : d.get_spawn_check_loc_list()) {
			pw.println("    - " + check.getX() + "," + check.getY() + ","
					+ check.getZ());

		}
		pw.println();

		pw.println("  " + "spawn_monster:");

		int monster_index = 0;
		for (InvMonsterData mob_data : d.get_mob_data()) {

			pw.println("    " + "monster" + (monster_index++) + ":");

			pw.println("      " + "monster_type: "
					+ mob_data.get_monster_type().toString());
			Location sloc = mob_data.get_spawn_location();
			pw.println("      " + "location: " + sloc.getX() + ","
					+ sloc.getY() + "," + sloc.getZ());
			pw.println("      " + "health: " + mob_data.get_health());
			pw.println("      " + "move_speed: " + mob_data.get_move_speed());
			pw.println("      " + "jump_level: " + mob_data.get_jump_lv());
			pw.println();
		}

		pw.close();
		plugin.getLogger()
				.info("Saving Invasion data is correctly completed!!");

	}
}
