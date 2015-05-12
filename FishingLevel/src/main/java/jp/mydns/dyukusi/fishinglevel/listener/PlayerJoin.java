package jp.mydns.dyukusi.fishinglevel.listener;

import jp.mydns.dyukusi.fishinglevel.FishingLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoin implements Listener {

	FishingLevel plugin;

	public PlayerJoin(FishingLevel fishingLevel) {
		this.plugin = fishingLevel;
	}

	@EventHandler
	void Join(PlayerLoginEvent event) {
		Player player = event.getPlayer();

		// new player
		if (!plugin.get_playerfishleveldata().containsKey(player.getName())) {
			plugin.register_new_player(player);
		}
		
	}
}
