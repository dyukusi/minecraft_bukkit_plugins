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
	private int small_x, small_y, small_z;
	private int big_x, big_y, big_z;
	boolean ignore_y;
	boolean can_buy;

	public AreaInformation(String AreaName, String owner, int price,
			Location first, Location second, boolean ignore_y) {

		this.area_name = AreaName;
		this.world_name = first.getWorld().getName();
		this.owner = owner;
		this.price = price;

		// big
		this.big_x = Math.max(first.getBlockX(), second.getBlockX());
		this.big_y = Math.max(first.getBlockY(), second.getBlockY());
		this.big_z = Math.max(first.getBlockZ(), second.getBlockZ());

		// small
		this.small_x = Math.min(first.getBlockX(), second.getBlockX());
		this.small_y = Math.min(first.getBlockY(), second.getBlockY());
		this.small_z = Math.min(first.getBlockZ(), second.getBlockZ());

		this.ignore_y = ignore_y;
		this.can_buy = false;

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
		set_can_buy(false);

		// twice price
		this.price *= 2;
	}
	
	public boolean get_can_buy(){
		return this.can_buy;
	}
	
	public void set_can_buy(boolean canbuy){
		this.can_buy = canbuy;
	}

}
