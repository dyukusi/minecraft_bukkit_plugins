package jp.mydns.dyukusi.biomenotificator.translate;

public enum Language_EtoJ {
	SWAMPLAND("湿地"),
	FOREST("森林"),
	TAIGA("タイガ"),
	DESERT("砂漠"),
	PLAINS("草原"),
	HELL("無間地獄"),
	SKY("神竜の住処"),
	OCEAN("海洋"),
	RIVER("河川"),
	EXTREME_HILLS("山岳地帯"),
	FROZEN_OCEAN("氷海"),
	FROZEN_RIVER("氷川"),
	ICE_PLAINS("氷河"),
	ICE_MOUNTAINS("雪山"),
	MUSHROOM_ISLAND("キノコ群生島"),
	MUSHROOM_SHORE("キノコ群生島の海辺"),
	BEACH("海岸"),
	DESERT_HILLS("砂漠の丘"),
	FOREST_HILLS("森の丘"),
	TAIGA_HILLS("タイガの丘"),
	SMALL_MOUNTAINS("緩やかな山岳地帯"),
	JUNGLE("熱帯雨林"),
	JUNGLE_HILLS("熱帯雨林の丘"),
	JUNGLE_EDGE("熱帯雨林の端"),
	DEEP_OCEAN("深海"),
	STONE_BEACH("岩礁"),
	COLD_BEACH("極寒の海岸"),
	BIRCH_FOREST("白樺の森"),
	BIRCH_FOREST_HILLS("白樺森の丘"),
	ROOFED_FOREST("覆われた森林"),
	COLD_TAIGA("極寒のタイガ"),
	COLD_TAIGA_HILLS("極寒のタイガの丘"),
	MEGA_TAIGA("メガタイガ"),
	MEGA_TAIGA_HILLS("メガタイガの丘"),
	EXTREME_HILLS_PLUS("険しい山岳地帯"),
	SAVANNA("サバンナ"),
	SAVANNA_PLATEAU("サバンナ台地"),
	MESA("メサ"),
	MESA_PLATEAU_FOREST("メサ台地森林地帯"),
	MESA_PLATEAU("メサ台地"),
	SUNFLOWER_PLAINS("ひまわり平原"),
	DESERT_MOUNTAINS("潤いの砂漠"),
	FLOWER_FOREST("花の楽園"),
	TAIGA_MOUNTAINS("険しいタイガ"),
	SWAMPLAND_MOUNTAINS("険しい湿地"),
	ICE_PLAINS_SPIKES("樹氷氷原"),
	JUNGLE_MOUNTAINS("険しい熱帯雨林"),
	JUNGLE_EDGE_MOUNTAINS(""),
	COLD_TAIGA_MOUNTAINS("険しい極寒のタイガ"),
	SAVANNA_MOUNTAINS("サバンナ山岳地帯"),
	SAVANNA_PLATEAU_MOUNTAINS("険しいサバンナ台地"),
	MESA_BRYCE("険しいメサ"),
	MESA_PLATEAU_FOREST_MOUNTAINS("メサ台地森林山岳地帯"),
	MESA_PLATEAU_MOUNTAINS("メサ台地山岳地帯"),
	BIRCH_FOREST_MOUNTAINS("険しい白樺森"),
	BIRCH_FOREST_HILLS_MOUNTAINS("白樺森の岳"),
	ROOFED_FOREST_MOUNTAINS("険しい覆われた森林"),
	MEGA_SPRUCE_TAIGA("巨大松タイガ"),
	EXTREME_HILLS_MOUNTAINS("砂利山岳地帯"),
	EXTREME_HILLS_PLUS_MOUNTAINS("険しい砂利山岳地帯"),
	MEGA_SPRUCE_TAIGA_HILLS("セコイア　タイガの丘");
	
	private String biome_jp;
	
	private Language_EtoJ(String biome) {
		this.biome_jp = biome;
	}
	
	public String get_inJapanese(){
		return this.biome_jp;
	}

}
