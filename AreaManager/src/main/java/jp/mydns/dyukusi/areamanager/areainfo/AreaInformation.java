package jp.mydns.dyukusi.areamanager.areainfo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class AreaInformation {
	String area_name;
	String custom_area_name;
	String world_name;
	String owner;
	int price;
	int last_played_time;
	private int f_x, f_y, f_z;
	private int s_x, s_y, s_z;

	private int small_x, small_y, small_z;
	private int big_x, big_y, big_z;
	boolean ignore_y;
	boolean owner_want_to_sell;
	boolean can_buy;

	public AreaInformation(String AreaName, String CustomAreaNAME,
			String owner, int price, Location first, Location second,
			boolean ignore_y, boolean can_buy, int last_played) {

		this.area_name = AreaName;
		this.custom_area_name = CustomAreaNAME;			
		this.world_name = first.getWorld().getName();
		this.owner = owner;
		this.price = price;

		// first position
		this.f_x = first.getBlockX();
		this.f_y = first.getBlockY();
		this.f_z = first.getBlockZ();

		// second position
		this.s_x = second.getBlockX();
		this.s_y = second.getBlockY();
		this.s_z = second.getBlockZ();

		// big
		this.big_x = Math.max(first.getBlockX(), second.getBlockX());
		this.big_y = Math.max(first.getBlockY(), second.getBlockY());
		this.big_z = Math.max(first.getBlockZ(), second.getBlockZ());

		// small
		this.small_x = Math.min(first.getBlockX(), second.getBlockX());
		this.small_y = Math.min(first.getBlockY(), second.getBlockY());
		this.small_z = Math.min(first.getBlockZ(), second.getBlockZ());

		this.ignore_y = ignore_y;
		this.owner_want_to_sell = false;
		this.can_buy = can_buy;

		// time
		this.last_played_time = last_played;

		// int sec = (int) (System.currentTimeMillis() / 1000);
		// int min = sec / 60;
		// int hour = min / 60;
		// this.last_played_time = hour;
	}

	public boolean get_owner_want_to_sell() {
		return this.owner_want_to_sell;
	}

	public void set_owner_want_to_sell(boolean set) {
		this.owner_want_to_sell = set;
	}

	public int get_price() {
		return this.price;
	}

	public String get_owner_name() {
		return this.owner;
	}

	public String set_custom_area_name(String name) {
		return this.custom_area_name = name;
	}

	public String get_custom_area_name() {
		return this.custom_area_name;
	}

	public boolean is_in_area(Entity player) {
		Location location = player.getLocation();
		int p_x = location.getBlockX(), p_y = location.getBlockY(), p_z = location
				.getBlockZ();

		// player is in area
		if (small_x <= p_x && p_x <= big_x && small_z <= p_z && p_z <= big_z) {

			// consider y?
			if (!ignore_y) {
				// not in range?
				if (!(small_y <= p_y && p_y <= big_y)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public String get_area_name() {
		return this.area_name;
	}

	public Location[] get_location() {
		Location loc[] = {
				new Location(Bukkit.getWorld(this.world_name), this.small_x,
						small_y, small_z),
				new Location(Bukkit.getWorld(this.world_name), this.big_x,
						big_y, big_z) };
		return loc;
	}

	public String get_range_str() {
		return "[ " + small_x + " ~ " + big_x + ", " + small_y + " ~ " + big_y
				+ ", " + small_z + " ~ " + big_z + " ]";
	}

	public boolean get_ignore_y() {
		return this.ignore_y;
	}

	public void set_ignore_y(boolean ignore) {
		this.ignore_y = ignore;
	}

	public void reset_owner() {
		this.owner = "none";
	}

	public void set_price(int new_price) {
		this.price = new_price;
	}

	public void buy_land(Player player, ProtectedRegion region) {
		this.owner = player.getName();

		DefaultDomain domain = new DefaultDomain();
		domain.addPlayer(player.getUniqueId());
		region.setOwners(domain);

		this.set_owner_want_to_sell(false);
		this.set_can_buy(false);

		// twice price
		this.price *= 2;
	}

	public boolean get_can_buy() {
		return this.can_buy;
	}

	public void set_can_buy(boolean canbuy) {
		this.can_buy = canbuy;
	}

	public String get_world_name() {
		return this.world_name;
	}

	public int[] get_first_position() {
		return new int[] { this.f_x, this.f_y, this.f_z };
	}

	public int[] get_second_position() {
		return new int[] { this.s_x, this.s_y, this.s_z };
	}

	public void set_last_played_time(int hour) {
		this.last_played_time = hour;
	}

	public int get_last_played_time() {
		return this.last_played_time;
	}

}
