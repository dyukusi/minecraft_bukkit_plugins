package jp.mydns.dyukusi.invasion.custommonster_base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import net.minecraft.server.v1_8_R1.PathfinderGoal;

public class PathfinderGoalWalktoTile extends PathfinderGoal {

	double speed;
	private EntityCreature entitycreature;
	ArrayList<Location> dest_list;
	int current_dest;
	boolean done = false;
	int i = 0;
	final double range = 20.0;

	public PathfinderGoalWalktoTile(EntityCreature entitycreature,
			List<Location> dest_list, double d) {
		this.speed = d;
		this.entitycreature = entitycreature;
		this.dest_list = (ArrayList<Location>) dest_list;

		this.current_dest = Math.abs(new Random().nextInt()) % dest_list.size();

		this.entitycreature.getAttributeInstance(GenericAttributes.b).setValue(
				this.range);

	}

	@Override
	public void c() {

	}

	@Override
	public void e() // setup
	{
		// this.entitycreature.getNavigation().a(-208, 85, 197, speed);
		this.i++;
		i = i % 13;

		if (i == 0) {
			Location entity_loc = this.entitycreature.getBukkitEntity()
					.getLocation();
			Location dest = dest_list.get(current_dest);

			double[] dest_dir = get_temp_dest(entity_loc, dest, (int) range - 4);

			double next_x = this.entitycreature.getBukkitEntity().getLocation()
					.getX()
					+ dest_dir[0];
			double next_z = this.entitycreature.getBukkitEntity().getLocation()
					.getZ()
					+ dest_dir[1];

			this.entitycreature.getNavigation().a(next_x, dest.getBlockY(),
					next_z, speed);

		}
	}

	@Override
	public boolean b() {

		Location dest = dest_list.get(current_dest);

		// reach the current dest
		if (this.entitycreature.getBukkitEntity().getLocation().distance(dest) <= 3) {
			current_dest = Math.abs(new Random().nextInt()) % dest_list.size();

		}

		return false;
	}

	@Override
	public boolean a() {

		Entity target;

		if ((target = this.entitycreature.getGoalTarget()) != null) {

			double distance_to_target = target
					.getBukkitEntity()
					.getLocation()
					.distance(
							this.entitycreature.getBukkitEntity().getLocation());

			// Bukkit.getServer().getLogger()
			// .info("DistanceToTarget: " + distance_to_target);

			if (distance_to_target <= range) {

				return false;
			}
		}

		// no target near the monster
		return true;

	}

	static public double[] get_temp_dest(Location loc1, Location loc2,
			int distance) {

		// Bukkit.getServer()
		// .getLogger()
		// .info(Math.abs(loc1.getY() - loc2.getBlockY()) + " , "
		// + Math.abs(loc1.getX() - loc2.getBlockX()));

		double radian = Math.atan2(loc2.getZ() - loc1.getZ(), loc2.getX()
				- loc1.getX());

		double result_x = Math.cos(radian) * distance;
		double result_z = Math.sin(radian) * distance;

		double result[] = { result_x, result_z };

		return result;
	}

	static public double get_degree(Location loc1, Location loc2) {
		double radian = Math.atan2(loc2.getZ() - loc1.getZ(), loc2.getX()
				- loc1.getX());
		return radian * 180d / Math.PI;
	}

}
