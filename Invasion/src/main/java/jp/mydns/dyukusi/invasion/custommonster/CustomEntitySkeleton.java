package jp.mydns.dyukusi.invasion.custommonster;

import java.lang.reflect.Field;
import java.util.List;

import jp.mydns.dyukusi.invasion.custommonster_base.PathfinderGoalWalktoTile;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class CustomEntitySkeleton extends EntitySkeleton {

	public CustomEntitySkeleton(World world, List<Location> dest_list) {
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
		} catch (IllegalArgumentException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {		
			e.printStackTrace();
		} catch (NoSuchFieldException e) {			
			e.printStackTrace();
		} catch (SecurityException e) {			
			e.printStackTrace();
		}

		// float in order not to drown
		this.goalSelector.a(1, new PathfinderGoalFloat(this));

		// attack
//		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(
				this, EntityHuman.class, true));

		// move to specified point
		this.goalSelector.a(3, new PathfinderGoalWalktoTile(this, dest_list, 3.0D));

		// this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
		// this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
		this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));

		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this,
				EntityHuman.class, 8.0F));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

	}

	@Override
	public void a(EntityLiving entity, float f) {
		super.a(entity, f); // Shoot an arrow from the normal skeleton class
	}

}
