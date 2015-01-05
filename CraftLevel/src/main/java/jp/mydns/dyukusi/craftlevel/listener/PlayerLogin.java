package jp.mydns.dyukusi.craftlevel.listener;

import jp.mydns.dyukusi.craftlevel.CraftLevel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

	CraftLevel plugin;

	public PlayerLogin(CraftLevel p) {
		this.plugin = p;
	}

	@EventHandler
	void SetExperienceToPlayerMetaData(PlayerLoginEvent event) {
		Player player = event.getPlayer();

		if (!plugin.get_player_crafting_level_info_contains(player)) {
			plugin.put_new_player_to_crafting_level_info(player);
		}
	}
}
