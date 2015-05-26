package jp.mydns.dyukusi.invasion.invdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.task.invasion_task;

import org.bukkit.Location;

public class InvasionData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String invasion_name;
	private int duration_sec = 0;
	private List<InvMonsterData> mob_data;
	private List<Location> dest_list;
	private List<Location> spawn_check_loc_list;

	public InvasionData(String inv_name) {
		this.invasion_name = inv_name;
		this.duration_sec = 10;
		this.mob_data = new ArrayList<InvMonsterData>();
		this.dest_list = new ArrayList<Location>();
		this.spawn_check_loc_list = new ArrayList<Location>();
	}

	public void add_spawn_check_location(Location location) {
		this.spawn_check_loc_list.add(location);
	}

	public List<Location> get_spawn_check_loc_list() {
		return this.spawn_check_loc_list;
	}

	public void add_destination(Location location) {
		this.dest_list.add(location);
	}

	public void set_duration(int duration) {
		this.duration_sec = duration;
	}

	public void add_monster(InvMonsterData data) {
		this.mob_data.add(data);
	}

	public void start_invation(Invasion plugin) {
		new invasion_task(plugin, this);
	}

	public String get_invasion_name() {
		return this.invasion_name;
	}

	public int get_duration_sec() {
		return this.duration_sec;
	}

	public List<InvMonsterData> get_mob_data() {
		return this.mob_data;
	}

	public List<Location> get_dest_list() {
		return this.dest_list;
	}

}
