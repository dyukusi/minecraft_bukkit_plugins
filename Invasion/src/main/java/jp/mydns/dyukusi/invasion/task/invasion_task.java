package jp.mydns.dyukusi.invasion.task;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class invasion_task extends BukkitRunnable {

	long start_time_sec;
	long end_time_sec;
	Invasion plugin;
	InvasionData inv;

	public invasion_task(Invasion plugin, InvasionData invasionData) {
		this.plugin = plugin;
		this.inv = invasionData;

		this.start_time_sec = get_current_sec();
		this.end_time_sec = get_current_sec() + invasionData.get_duration_sec();

	}

	@Override
	public void run() {

		// spawn monster
		for (InvMonsterData data : this.inv.get_mob_data()) {
			data.spawn(plugin, data.get_spawn_location(), inv.get_dest_list());
		}

		// start invasion loop per sec
		while (get_remain_time_sec() > 0) {
			plugin.getServer().broadcastMessage(
					"RemainTime: " + get_remain_time_sec());

			// wait 1 sec
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		plugin.getServer().broadcastMessage(
				ChatColor.GREEN
						+ "--------------Invasion stopped!--------------");

	}

	private long get_current_sec() {
		return System.currentTimeMillis() / 1000;
	}

	private long get_remain_time_sec() {
		return this.end_time_sec - get_current_sec();
	}

}
