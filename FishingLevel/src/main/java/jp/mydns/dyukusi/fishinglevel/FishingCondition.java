package jp.mydns.dyukusi.fishinglevel;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FishingCondition {

	FishingPoint1(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),40),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),10),
			new CaughtItem(new ItemStack(Material.COOKED_CHICKEN, 3, (short) 0),10),
			new CaughtItem(new ItemStack(Material.CHAINMAIL_BOOTS, 1, (short) 0),2),
			new CaughtItem(new ItemStack(Material.LEATHER_HELMET, 1),2),
			new CaughtItem(new ItemStack(Material.LEATHER_BOOTS, 1),2),
			new CaughtItem(new ItemStack(Material.LEATHER_LEGGINGS, 1),2),
			new CaughtItem(new ItemStack(Material.LEATHER_CHESTPLATE, 1),2)}),
					
	FishingPoint2(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),50),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),20),
			new CaughtItem(new ItemStack(Material.YELLOW_FLOWER, 1, (short) 0),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 0),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 1),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 2),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 3),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 4),4),
			new CaughtItem(new ItemStack(Material.RED_ROSE, 1, (short) 5),4),
			new CaughtItem(new ItemStack(Material.IRON_INGOT, 3, (short) 0),2)}	
			),
		
	FishingPoint3(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),30),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),30),
			new CaughtItem(new ItemStack(Material.COBBLESTONE, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.MOSSY_COBBLESTONE, 1, (short) 3),8),
			new CaughtItem(new ItemStack(Material.SAPLING, 1, (short) 0),4),
			new CaughtItem(new ItemStack(Material.SAPLING, 1, (short) 1),4),
			new CaughtItem(new ItemStack(Material.SAPLING, 1, (short) 2),4),
			new CaughtItem(new ItemStack(Material.SAPLING, 1, (short) 4),4),
			new CaughtItem(new ItemStack(Material.SAPLING, 1, (short) 3),4),
			new CaughtItem(new ItemStack(Material.GOLD_INGOT, 3, (short) 0),2)}	
			),
	
	FishingPoint4(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),25),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),25),
			new CaughtItem(new ItemStack(Material.ROTTEN_FLESH, 1, (short) 0),15),
			new CaughtItem(new ItemStack(Material.RAW_CHICKEN, 1, (short) 0),15),
			new CaughtItem(new ItemStack(Material.RAW_BEEF, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.RABBIT, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.MUTTON, 1, (short) 2),5),
			new CaughtItem(new ItemStack(Material.PORK, 1, (short) 4),3),
			new CaughtItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1),1)}	
			),
	FishingPoint5(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),30),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),20),
			new CaughtItem(new ItemStack(Material.BONE, 1, (short) 0),20),
			new CaughtItem(new ItemStack(Material.SPIDER_EYE, 1, (short) 0),20),
			new CaughtItem(new ItemStack(Material.CLAY, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.WEB, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.SLIME_BALL, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.MAGMA_CREAM, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.EYE_OF_ENDER, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.GHAST_TEAR, 1, (short) 1),1)}	
	),
	
	FishingPoint6(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),40),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),20),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),20),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),10),
			new CaughtItem(new ItemStack(Material.COOKED_FISH, 3, (short) 0),5),
			new CaughtItem(new ItemStack(Material.CHAINMAIL_HELMET, 1),1),
			new CaughtItem(new ItemStack(Material.IRON_BOOTS, 1),1),
			new CaughtItem(new ItemStack(Material.GOLD_HELMET, 1),1),
			new CaughtItem(new ItemStack(Material.DIAMOND_ORE, 1),1),
			new CaughtItem(new ItemStack(Material.SKULL, 1),1)}
	),
	
	FishingPoint7(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),40),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),15),
			new CaughtItem(new ItemStack(Material.COOKED_FISH, 3, (short) 0),10),
			new CaughtItem(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1),1),
			new CaughtItem(new ItemStack(Material.GOLD_BOOTS, 1),1),
			new CaughtItem(new ItemStack(Material.IRON_HELMET, 1),1),
			new CaughtItem(new ItemStack(Material.GOLD_LEGGINGS, 1),1),
			new CaughtItem(new ItemStack(Material.DIAMOND_ORE, 1),1)}
	),
	
	FishingPoint8(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),50),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),50),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),50),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),50),
			new CaughtItem(new ItemStack(Material.COOKED_FISH, 3, (short) 0),10),
			new CaughtItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1),1),
			new CaughtItem(new ItemStack(Material.IRON_LEGGINGS, 1),1),
			new CaughtItem(new ItemStack(Material.IRON_CHESTPLATE, 1),1),
			new CaughtItem(new ItemStack(Material.DIAMOND_ORE, 1),1),
			new CaughtItem(new ItemStack(Material.DIAMOND, 1),1)}
	),
	
	FishingPoint9(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.DIRT, 1, (short) 0),25),					
			new CaughtItem(new ItemStack(Material.COBBLESTONE, 1, (short) 0),15),
			new CaughtItem(new ItemStack(Material.STONE, 1, (short) 1),14),
			new CaughtItem(new ItemStack(Material.STONE, 1, (short) 3),10),
			new CaughtItem(new ItemStack(Material.STONE, 3, (short) 5),10),
			new CaughtItem(new ItemStack(Material.SANDSTONE, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.COAL_ORE, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.IRON_ORE, 1, (short) 0),3),
			new CaughtItem(new ItemStack(Material.GOLD_ORE, 1, (short) 0),2),
			new CaughtItem(new ItemStack(Material.DIAMOND_ORE, 1, (short) 0),1)}
	),

	FishingPoint10(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),35),					
			new CaughtItem(new ItemStack(Material.WOOD_SWORD, 1),15),
			new CaughtItem(new ItemStack(Material.WOOD_AXE, 1),15),
			new CaughtItem(new ItemStack(Material.WOOD_PICKAXE, 1, (short) 3),15),
			new CaughtItem(new ItemStack(Material.WOOD_HOE, 1, (short) 5),11),
			new CaughtItem(new ItemStack(Material.IRON_SWORD, 1),4),
			new CaughtItem(new ItemStack(Material.IRON_PICKAXE, 1),3),
			new CaughtItem(new ItemStack(Material.BOW, 0),1),
			new CaughtItem(new ItemStack(Material.DIAMOND_ORE, 1),1),
			new CaughtItem(new ItemStack(Material.GOLDEN_CARROT, 1),1)}
	),
	
	FishingPoint11(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),30),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),15),
			new CaughtItem(new ItemStack(Material.PRISMARINE_CRYSTALS, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.PRISMARINE_SHARD, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.PRISMARINE, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.PRISMARINE, 1, (short) 1),5),
			new CaughtItem(new ItemStack(Material.PRISMARINE, 1, (short) 2),4),
			new CaughtItem(new ItemStack(Material.SEA_LANTERN, 1, (short) 0),1)}
	),
	
	FishingPoint12(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),70),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),22),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 15),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 1),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 2),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 3),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 4),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 5),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 6),1)}
	),
	
	FishingPoint13(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),50),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),42),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 7),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 8),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 9),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 10),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 11),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 12),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 13),1),
			new CaughtItem(new ItemStack(Material.BANNER, 1, (short) 14),1)}
	),
	
	FishingPoint14(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.EGG, 1, (short) 0),91),					
			new CaughtItem(new ItemStack(Material.RECORD_3, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_4, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_5, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_6, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_7, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_8, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_9, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_10, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.RECORD_11, 1, (short) 0),1)}
	),
	
	FishingPoint15(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.ROTTEN_FLESH, 1, (short) 0),60),					
			new CaughtItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.FLINT, 1),5),
			new CaughtItem(new ItemStack(Material.ACACIA_DOOR_ITEM, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.PAPER, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.LEATHER, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.SADDLE, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.SUGAR_CANE, 1, (short) 0),5),
			new CaughtItem(new ItemStack(Material.GLOWSTONE_DUST, 1, (short) 0),4),
			new CaughtItem(new ItemStack(Material.COMPASS, 1, (short) 0),1)}
	),
	
	FishingPoint16(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),50),					
			new CaughtItem(new ItemStack(Material.GLASS_BOTTLE, 1, (short) 0),42),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 16),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 32),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 64),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8193),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8194),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8195),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8196),1)}
	),
	
	FishingPoint17(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),91),					
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8197),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8198),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8200),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8201),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8202),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8204),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8205),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8206),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8225),1)}
	),
	
	FishingPoint18(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),91),					
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8226),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8228),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8229),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8233),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8235),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8236),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8257),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8258),1),
			new CaughtItem(new ItemStack(Material.POTION, 1, (short) 8259),1)}
	),
	
	FishingPoint19(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),15),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),15),
			new CaughtItem(new ItemStack(Material.TRIPWIRE_HOOK, 1, (short) 0),15),
			new CaughtItem(new ItemStack(Material.CARROT_ITEM, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.SUGAR, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.SHEARS, 1, (short) 0),3),
			new CaughtItem(new ItemStack(Material.ENDER_PEARL, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.EMPTY_MAP, 1, (short) 0),1)}
	),
	
	FishingPoint20(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),30),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),20),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),15),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),10),
			new CaughtItem(new ItemStack(Material.LEVER, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.BOW, 1),7),
			new CaughtItem(new ItemStack(Material.ARROW, 1, (short) 0),3),
			new CaughtItem(new ItemStack(Material.IRON_SPADE, 1),2),
			new CaughtItem(new ItemStack(Material.IRON_AXE, 1),2),
			new CaughtItem(new ItemStack(Material.DIAMOND_HOE, 1),1)}
	),
	
	FishingPoint21(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),70),					
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 1),10),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 2),10),
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 3),4),
			new CaughtItem(new ItemStack(Material.NETHER_BRICK, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.NETHER_FENCE, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.NETHER_WARTS, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.BREWING_STAND_ITEM, 1),1),
			new CaughtItem(new ItemStack(Material.IRON_DOOR, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.IRON_BLOCK, 1, (short) 0),1)}
	),
	
	FishingPoint22(new CaughtItem[] { 
			new CaughtItem(new ItemStack(Material.RAW_FISH, 1, (short) 0),35),					
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 0),10),
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 1),10),
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 2),10),
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 3),10),
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 4),10),
			new CaughtItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 5),10),
			new CaughtItem(new ItemStack(Material.NAME_TAG, 1, (short) 0),3),
			new CaughtItem(new ItemStack(Material.GOLD_RECORD, 1, (short) 0),1),
			new CaughtItem(new ItemStack(Material.GREEN_RECORD, 1, (short) 0),1)}
	);

	CaughtItem items[];

	FishingCondition(CaughtItem items[]) {
		this.items = items;
	}
	
	public CaughtItem[] get_items(){
		return items;
	}
	
}
