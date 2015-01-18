package jp.mydns.dyukusi.areanotificator.custominfo;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CustomAreaInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String area_name;
	private String subtitle;
	private String world_name;
	private int f_x, f_y, f_z;
	private int s_x, s_y, s_z;
	private int small_x, small_y, small_z;
	private int big_x, big_y, big_z;
	private int register_order;
	private boolean display_as_subtitle;

	public CustomAreaInfo(String AREA_NAME, String CREATER_NAME,
			Location first, Location second) {

		// basic info
		this.area_name = AREA_NAME;
		this.subtitle = CREATER_NAME;
		this.world_name = first.getWorld().getName();
		this.display_as_subtitle = false;
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

	public boolean get_as_subtitle() {
		return this.display_as_subtitle;
	}

	public boolean is_in_area(Entity player) {
		Location location = player.getLocation();
		int p_x = location.getBlockX(), p_y = location.getBlockY(), p_z = location
				.getBlockZ();

		// player is in area (ignoring y)
		if (small_x <= p_x && p_x <= big_x && small_z <= p_z && p_z <= big_z) {
			return true;
		}

		return false;
	}

	public String get_area_name() {
		return this.area_name;
	}

	public String get_creater_name() {
		return this.subtitle;
	}

	public void as_subtitle() {
		this.display_as_subtitle = true;
	}

}
