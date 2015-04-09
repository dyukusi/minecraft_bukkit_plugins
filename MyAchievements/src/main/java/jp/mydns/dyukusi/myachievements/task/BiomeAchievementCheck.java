package jp.mydns.dyukusi.myachievements.task;

import java.util.HashMap;

import jp.mydns.dyukusi.myachievements.MyAchievements;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class BiomeAchievementCheck extends BukkitRunnable {

	MyAchievements plugin;
	Player player;
	HashMap<String, Boolean> map;
	MyStat mystat;

	public BiomeAchievementCheck(MyAchievements plugin, Player player,
			HashMap<String, Boolean> hashMap, MyStat mystat) {
		this.plugin = plugin;
		this.player = player;
		this.map = hashMap;
		this.mystat = mystat;
	}

	@Override
	public void run() {
		Location loc = player.getLocation();

		// not in custom area?
		if (player.hasMetadata("area_isCustom")) {
			if (player.getMetadata("area_isCustom").size() > 0
					&& !player.getMetadata("area_isCustom").get(0).asBoolean()) {

				String current = player.getMetadata("area").get(0).asString();

				// new biome for the player
				if (!map.containsKey(current)) {
					map.put(current, true);
					player.playSound(loc, Sound.ITEM_PICKUP, 1F, 0.5F);
					player.sendMessage(ChatColor.GREEN + "新しいバイオーム"
							+ ChatColor.WHITE + current + ChatColor.GREEN
							+ "を見つけた！  " + ChatColor.WHITE + "( "
							+ ChatColor.GOLD + map.size() + ChatColor.WHITE
							+ "/" + Biome.values().length + ChatColor.WHITE
							+ " )");
					player.sendMessage(ChatColor.AQUA
							+ "< You found a new biome! >");

				}
			}

			player.setMetadata("visit_biome_num", new FixedMetadataValue(
					plugin, map.size()));
		}

		// buy from someone?
		if (player.hasMetadata("buy")) {
			String data = player.getMetadata("buy").get(0).asString();
			String array[] = data.split(",");
			String from = array[0];
			int price = Integer.parseInt(array[1]);

			if (!mystat.get_sell_total_map().containsKey(from)) {
				mystat.get_sell_total_map().put(from, 0);
			}

			mystat.get_sell_total_map().put(from,
					mystat.get_sell_total_map().get(from) + price);

			player.removeMetadata("buy", plugin);
		}

	}
}
