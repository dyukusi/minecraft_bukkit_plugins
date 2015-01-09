package jp.mydns.dyukusi.biomenotificator.task;

import jp.mydns.dyukusi.biomenotificator.BiomeNotificator;
import jp.mydns.dyukusi.biomenotificator.translate.Language_EtoJ;
import jp.mydns.dyukusi.title.Title;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
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

		// change player current biome
		if (!plugin.isSame_biome_with_last_time(player)) {
			Biome current = plugin.get_current_biome(player);
			player.removeMetadata("biome", plugin);
			plugin.set_meta_current_biome(player);

			Title title = new Title(Language_EtoJ.valueOf(current.name()).get_inJapanese(), "< " + current.name()
					+ " >");
			title.setTitleColor(ChatColor.GOLD);
			title.setSubtitleColor(ChatColor.AQUA);
			title.send(player);
			player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 1.5F, 0.5F);

		}

	}

}
