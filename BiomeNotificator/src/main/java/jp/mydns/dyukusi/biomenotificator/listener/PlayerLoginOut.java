package jp.mydns.dyukusi.biomenotificator.listener;

import jp.mydns.dyukusi.biomenotificator.BiomeNotificator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginOut implements Listener {

	BiomeNotificator plugin;

	public PlayerLoginOut(BiomeNotificator biomeNotificator) {
		plugin = biomeNotificator;
	}

	@EventHandler
	void PlayerJoinListener(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.run_notificator(player, 0, true);
	}

	@EventHandler
	void PlayerLeaveListener(PlayerQuitEvent event) {
		Player player = event.getPlayer();	
		player.removeMetadata("biome", plugin);
	}

}
