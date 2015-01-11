package jp.mydns.dyukusi.regenerationpoint.regeneinfo;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegeneInfo implements Serializable {

	String world_name;
	private int f_x, f_y, f_z;
	private int s_x, s_y, s_z;
	private int big_x, big_y, big_z;
	private int small_x, small_y, small_z;

	public RegeneInfo(Location first, Location second) {
		// basic info
		this.world_name = first.getWorld().getName();
		this.f_x = first.getBlockX();
		this.f_y = first.getBlockY();
		this.f_z = first.getBlockZ();
		this.s_x = second.getBlockX();
		this.s_y = second.getBlockY();
		this.s_z = second.getBlockZ();

		// big
		big_x = Math.max(f_x, s_x);
		big_y = Math.max(f_y, s_y);
		big_z = Math.max(f_z, s_z);

		// small
		small_x = Math.min(f_x, s_x);
		small_y = Math.min(f_y, s_y);
		small_z = Math.min(f_z, s_z);
	}

	public boolean is_in_area(Player player) {
		Location location = player.getLocation();
		int p_x = location.getBlockX(), p_y = location.getBlockY(), p_z = location.getBlockZ();

		// player is in area (ignoring y)
		if (small_x <= p_x && p_x <= big_x && small_z <= p_z && p_z <= big_z) {
			return true;
		}

		return false;
	}

}
