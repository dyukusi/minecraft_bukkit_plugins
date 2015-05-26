package jp.mydns.dyukusi.invasion.task;

import java.util.LinkedList;
import java.util.List;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.custommonster_base.PathfinderGoalWalktoTile;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Check_spawnloc_task extends BukkitRunnable {

	Invasion plugin;
	Player player;
	InvasionData invd;
	Location center_of_dest;

	public Check_spawnloc_task(Invasion plugin, Player player,
			InvasionData invd, Location center_of_dest) {
		this.plugin = plugin;
		this.player = player;
		this.invd = invd;
		this.center_of_dest = center_of_dest;
	}

	@Override
	public void run() {

		List<Location> spawn_loc_list = new LinkedList<Location>();
		for (InvMonsterData mob : invd.get_mob_data()) {
			spawn_loc_list.add(mob.get_spawn_location());
		}

		Location back = player.getLocation();
		player.setGameMode(GameMode.SPECTATOR);

		for (Location loc : spawn_loc_list) {

			double[] tp_dest_relative = PathfinderGoalWalktoTile.get_temp_dest(
					loc, center_of_dest, 10);
			Location tp_dest = loc.add(tp_dest_relative[0], 0,
					tp_dest_relative[1]);
			tp_dest.setY(center_of_dest.getY());

			tp_dest.setPitch(45);
			tp_dest.setYaw((float) PathfinderGoalWalktoTile.get_degree(
					center_of_dest, tp_dest) - 90);

			player.teleport(tp_dest);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		player.teleport(back);
		player.setGameMode(GameMode.SURVIVAL);

	}
}
