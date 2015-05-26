package jp.mydns.dyukusi.invasion.custommonster_base;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.BiomeMeta;
import net.minecraft.server.v1_8_R1.EntityTypes;

public class getPfield_registerEnt {

	public static Object getPrivateField(String fieldName, Class clazz,
			Object object) {
		Field field;
		Object o = null;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

	public static void registerEntities() {
		for (CustomEntityType entity : CustomEntityType.values())
			/* Get our entities */
			a(entity.getCustomClass(), entity.getName(), entity.getID());
//		/* Get all biomes on the server */
//		BiomeBase[] biomes;
//		try {
//			biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
//		} catch (Exception exc) {
//			return;
//		}
//		for (BiomeBase biomeBase : biomes) {
//			if (biomeBase == null)
//				break;
//			for (String field : new String[] { "at", "au", "av", "aw" })
//				// Lists that hold all entity types
//				try {
//					Field list = BiomeBase.class.getDeclaredField(field);
//					list.setAccessible(true);
//					@SuppressWarnings("unchecked")
//					List<BiomeMeta> mobList = (List<BiomeMeta>) list
//							.get(biomeBase);
//
//					for (BiomeMeta meta : mobList)
//						for (CustomEntityType entity : CustomEntityType
//								.values())
//							if (entity.getNMSClass().equals(meta.b)) 																	 
//								meta.b = entity.getCustomClass(); // Set it's
//					// meta to
//					// our
//					// custom
//					// class's
//					// meta
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//		}
	}

	/*
	 * Method(add to onDisable()) to prevent server leaks when the plugin gets
	 * disabled
	 */
	@SuppressWarnings("rawtypes")
	public static void unregisterEntities() {
		for (CustomEntityType entity : CustomEntityType.values()) {
			// Remove our class references.
			try {
				((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity
						.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity
						.getCustomClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (CustomEntityType entity : CustomEntityType.values())
			try {
				a(entity.getNMSClass(), entity.getName(), entity.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}

		BiomeBase[] biomes;
		try {
			biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes"); /*
																				 * Get
																				 * all
																				 * biomes
																				 * again
																				 */
		} catch (Exception exc) {
			return;
		}
		for (BiomeBase biomeBase : biomes) {
			if (biomeBase == null)
				break;

			for (String field : new String[] { "at", "au", "av", "aw" })
				/* The entity list */
				try {
					Field list = BiomeBase.class.getDeclaredField(field);
					list.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<BiomeMeta> mobList = (List<BiomeMeta>) list
							.get(biomeBase);

					for (BiomeMeta meta : mobList)
						for (CustomEntityType entity : CustomEntityType
								.values())
							if (entity.getCustomClass().equals(meta.b))
								meta.b = entity.getNMSClass(); /*
																 * Set the
																 * entities meta
																 * back to the
																 * NMS one
																 */
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	/* A little Util for getting a private field */
	@SuppressWarnings("rawtypes")
	private static Object getPrivateStatic(Class clazz, String f)
			throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
	}

	/* Set data into the entitytypes class from NMS */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void a(Class paramClass, String paramString, int paramInt) {
		try {
			((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString,
					paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass,
					paramString);
//			((Map) getPrivateStatic(EntityTypes.class, "e")).put(
//					Integer.valueOf(paramInt), paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass,
					Integer.valueOf(paramInt));
//			((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString,
//					Integer.valueOf(paramInt));
		} catch (Exception exc) {
		}
	}

}
