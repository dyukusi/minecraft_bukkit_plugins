package jp.mydns.dyukusi.invasion.custommonster_base;

import java.util.List;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.custommonster.CustomEntitySkeleton;
import jp.mydns.dyukusi.invasion.custommonster.NormalCreeper;
import jp.mydns.dyukusi.invasion.custommonster.NormalZombie;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import net.minecraft.server.v1_8_R1.EntityCreeper;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.ItemStack;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;

public enum CustomEntityType {

	NORMALSKELETON("Skeleton", 51, EntityType.SKELETON, EntitySkeleton.class,
			CustomEntitySkeleton.class), NORMALZOMBIE("Zombie", 54,
			EntityType.ZOMBIE, EntityZombie.class, NormalZombie.class), NORMALCREEPER(
			"Creeper", 50, EntityType.CREEPER, EntityCreeper.class,
			NormalCreeper.class);

	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> nmsClass;
	private Class<? extends EntityInsentient> customClass;

	//
	private CustomEntityType(String name, int id, EntityType entityType,
			Class<? extends EntityInsentient> nmsClass, /* The custom class */
			Class<? extends EntityInsentient> customClass) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
	}

	public void spawnEntity(Invasion plugin, InvMonsterData mobdata,
			Location spawn_loc, List<Location> dest_list) {

		EntityInsentient entity = null;
		net.minecraft.server.v1_8_R1.World world = ((CraftWorld) Bukkit
				.getWorld("world")).getHandle();
		ItemStack helmet = CraftItemStack
				.asNMSCopy(new org.bukkit.inventory.ItemStack(
						Material.GOLD_HELMET));

		switch (mobdata.get_monster_type()) {
		case NORMALSKELETON:
			entity = new CustomEntitySkeleton(world, dest_list);

			// equip bow and helmet
			ItemStack bow = CraftItemStack
					.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.BOW,
							1));
			// org.bukkit.inventory.ItemStack testSwordItem = CraftItemStack
			// .asCraftMirror(testSword);
			entity.setEquipment(0, bow);

			break;

		case NORMALZOMBIE:
			entity = new NormalZombie(world, dest_list);
			break;
		case NORMALCREEPER:
			entity = new NormalCreeper(world, dest_list);

		default:
			break;
		}

		// set health
		entity.setHealth(mobdata.get_health());

		// equip helmet
//		entity.setEquipment(4, helmet);

		entity.getBukkitEntity().setMetadata("jump_lv",
				new FixedMetadataValue(plugin, mobdata.get_jump_lv()));

		// set position
		entity.setPositionRotation(spawn_loc.getX(), spawn_loc.getY(),
				spawn_loc.getZ(), spawn_loc.getYaw(), spawn_loc.getPitch());

		
		((LivingEntity)entity.getBukkitEntity()).setRemoveWhenFarAway(false);
		
		// entity.prepare(null,null);
		world.addEntity(entity, SpawnReason.CUSTOM);
		entity.p(entity);

		// world.addEntity(entity);
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public Class<? extends EntityInsentient> getNMSClass() {
		return nmsClass;
	}

	public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
	}

}
