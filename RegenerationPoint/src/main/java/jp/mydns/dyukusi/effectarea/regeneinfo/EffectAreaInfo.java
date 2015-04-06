package jp.mydns.dyukusi.effectarea.regeneinfo;

import java.io.Serializable;
import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EffectAreaInfo implements Serializable {

	String area_name;
	String world_name;
	private int f_x, f_y, f_z;
	private int s_x, s_y, s_z;
	private int big_x, big_y, big_z;
	private int small_x, small_y, small_z;
	private LinkedList<EffectInfo> effectinfo_list;
	private float walking_speed;
	private int hunger_regene;
	private boolean monster_spawn;

	public EffectAreaInfo(String area_name, Location first, Location second) {
		// basic info
		this.area_name = area_name;
		this.world_name = first.getWorld().getName();
		this.f_x = first.getBlockX();
		this.f_y = first.getBlockY();
		this.f_z = first.getBlockZ();
		this.s_x = second.getBlockX();
		this.s_y = second.getBlockY();
		this.s_z = second.getBlockZ();

		// 0 = disable food regeneration
		this.hunger_regene = 0;

		// default monster spawn : on
		this.monster_spawn = true;

		// default walking speed
		this.walking_speed = 0.2F;

		// init list
		this.effectinfo_list = new LinkedList<EffectInfo>();

		// big
		big_x = Math.max(f_x, s_x);
		big_y = Math.max(f_y, s_y);
		big_z = Math.max(f_z, s_z);

		// small
		small_x = Math.min(f_x, s_x);
		small_y = Math.min(f_y, s_y);
		small_z = Math.min(f_z, s_z);
	}

	public boolean get_monsterspawn() {
		return this.monster_spawn;
	}

	public boolean set_monsterspawn_toggle() {
		if (this.monster_spawn) {
			this.monster_spawn = false;
		} else {
			this.monster_spawn = true;
		}

		return this.monster_spawn;
	}

	public int get_hunger_regene_speed() {
		return this.hunger_regene;
	}

	public void set_hunger_regene_speed(int speed) {
		this.hunger_regene = speed;
	}

	public void set_walking_speed(float speed) {
		this.walking_speed = speed;
	}

	public float get_walking_speed() {
		return this.walking_speed;
	}

	public LinkedList<EffectInfo> get_effect_list() {
		return this.effectinfo_list;
	}

	public boolean is_in_area(Location loc) {
		Location location = loc;
		int p_x = location.getBlockX(), p_y = location.getBlockY(), p_z = location
				.getBlockZ();

		// player is in area (ignoring y)
		if (small_x <= p_x && p_x <= big_x && small_z <= p_z && p_z <= big_z) {
			return true;
		}

		return false;
	}

	public void add_effect(String effect_name, int level, int duration) {
		this.effectinfo_list.add(new EffectInfo(effect_name, level, duration));
	}

	public void remove_effects() {
		this.effectinfo_list.clear();
	}

}
