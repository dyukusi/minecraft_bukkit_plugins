package jp.mydns.dyukusi.biomenotificator.task;

import jp.mydns.dyukusi.biomenotificator.BiomeNotificator;
import jp.mydns.dyukusi.biomenotificator.custominfo.CustomAreaInfo;
import jp.mydns.dyukusi.biomenotificator.translate.Language_EtoJ;
import jp.mydns.dyukusi.title.Title;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Notificator extends BukkitRunnable {

	BiomeNotificator plugin;
	Player player;

	public Notificator(BiomeNotificator biomeNotificator, Player p) {
		this.plugin = biomeNotificator;
		this.player = p;
	}

	@Override
	public void run() {

		// player left the game
		if (!player.isOnline()) {
			this.cancel();
		}

		// change player current area
		if (!plugin.isSame_area_with_last_time(player)) {
			String current_area_name = plugin.get_current_area_name(player);
			player.removeMetadata("area", plugin);
			player.setMetadata("area", new FixedMetadataValue(plugin, current_area_name));

			boolean in_custom_area = false;
			try {
				Language_EtoJ.valueOf(current_area_name);
			} catch (IllegalArgumentException e) {
				// in custom area
				in_custom_area = true;
			}

			Title title = null;

			if (in_custom_area) {
				CustomAreaInfo info = plugin.get_customarea_info(current_area_name);
				title = new Title(info.get_area_name(), "by " + info.get_creater_name());
				title.setTitleColor(ChatColor.GREEN);
				title.setSubtitleColor(ChatColor.WHITE);
			} else {
				title = new Title(Language_EtoJ.valueOf(current_area_name).get_inJapanese(), "< " + current_area_name
						+ " >");
				title.setTitleColor(ChatColor.GOLD);
				title.setSubtitleColor(ChatColor.AQUA);

			}

			title.send(player);
			player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 1.5F, 0.5F);

		}

	}

}
