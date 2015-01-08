package jp.mydns.dyukusi.seasonalfood.listener;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	SeasonalFood plugin;

	public PlayerJoin(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
	}

	@EventHandler
	void PlayerJoinDisplaySeason(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.display_season(false, player, plugin.get_current_season());
	}

}
