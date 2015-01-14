package jp.mydns.dyukusi.areanotificator.listener;

import jp.mydns.dyukusi.areanotificator.AreaNotificator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginOut implements Listener {

	AreaNotificator plugin;

	public PlayerLoginOut(AreaNotificator biomeNotificator) {
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
	}

}
