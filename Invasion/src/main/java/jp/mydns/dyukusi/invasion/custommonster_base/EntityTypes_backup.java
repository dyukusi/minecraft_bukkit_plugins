package jp.mydns.dyukusi.invasion.custommonster_base;
//package jp.mydns.dyukusi.invasion.custommonster;
//
//import java.util.Map;
//
//import jp.mydns.dyukusi.invasion.Invasion;
//
//import org.bukkit.Location;
//import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
//
//import net.minecraft.server.v1_8_R1.Entity;
//
//public enum EntityTypes {
//	CUSTOM_ZOMBIE("Zombie", 60, CustomZombie.class);
//
//	private EntityTypes(String name, int id, Class<? extends Entity> custom) {
//		addToMaps(custom, name, id);
//	}
//
//	public static Entity spawnEntity(Entity entity, Location loc,
//			Invasion plugin) {
//		entity.setLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
//				loc.getYaw(), loc.getPitch());
//		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
//
//		plugin.getServer().broadcastMessage(
//				"spawnEntity(); in world( " + loc.getWorld().getName());
//
//		return entity;
//	}
//
//	private static void addToMaps(Class clazz, String name, int id) {
//		// getPrivateField is the method from above.
//		// Remove the lines with // in front of them if you want to override
//		// default entities (You'd have to remove the default entity from the
//		// map first though).
//
//		((Map<String, Class>) getPfield.getPrivateField("c",
//				net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(
//				name, clazz);
//
//		((Map<Class, String>) getPfield.getPrivateField("d",
//				net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(
//				clazz, name);
//
//		((Map<Class, Integer>) getPfield.getPrivateField("c",
//				net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(
//				clazz, Integer.valueOf(id));
//
//	}
//
//}