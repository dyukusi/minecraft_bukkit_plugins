package jp.mydns.dyukusi.invasion.custommonster;

import java.lang.reflect.Field;
import java.util.List;

import jp.mydns.dyukusi.invasion.custommonster_base.PathfinderGoalWalktoTile;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NormalZombie extends EntityZombie {

	public NormalZombie(World world, List<Location> dest_list) {
		// super(world);

		super(world);
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch (Exception exc) {
			exc.printStackTrace();
			// This means that the name of one of the fields changed names or
			// declaration and will have to be re-examined.
		}

		this.goalSelector.a(1, new PathfinderGoalFloat(this));

		// attack
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(
				this, EntityHuman.class, true));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this,
				EntityHuman.class, 2.0D, false));

		// move to specified point
		this.goalSelector.a(3, new PathfinderGoalWalktoTile(this, dest_list,
				1.0D));

		// this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
		// this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
		this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));

		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this,
				EntityHuman.class, 8.0F));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

	}

}
