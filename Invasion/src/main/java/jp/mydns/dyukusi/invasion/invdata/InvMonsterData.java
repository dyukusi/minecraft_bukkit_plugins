package jp.mydns.dyukusi.invasion.invdata;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.custommonster.CustomEntitySkeleton;
import jp.mydns.dyukusi.invasion.custommonster_base.CustomEntityType;

public class InvMonsterData implements Serializable {

	private static final long serialVersionUID = 1L;
	CustomEntityType monster;
	Location spawn_location;
	int health;
	double move_speed;
	int jump_lv;

	public InvMonsterData(CustomEntityType monster, Location spawn_location,
			int health, double move_speed, int jump_lv) {
		this.monster = monster;
		this.spawn_location = spawn_location;
		this.health = health;
		this.move_speed = move_speed;
		this.jump_lv = jump_lv;
	}

	public CustomEntityType get_monster_type() {
		return this.monster;
	}

	public int get_health() {
		return this.health;
	}

	public double get_move_speed() {
		return this.move_speed;
	}

	public int get_jump_lv() {
		return this.jump_lv;
	}

	public Location get_spawn_location() {
		return this.spawn_location;
	}

	public void spawn(Invasion plugin, Location spawn_loc,
			List<Location> dest_list) {
		this.monster.spawnEntity(plugin, this, spawn_loc, dest_list);
	}

}
