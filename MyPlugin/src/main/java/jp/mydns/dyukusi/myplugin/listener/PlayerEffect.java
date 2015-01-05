package jp.mydns.dyukusi.myplugin.listener;

import jp.mydns.dyukusi.myplugin.MyPlugin;
import jp.mydns.dyukusi.myplugin.task.FireProtection;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerEffect implements Listener {

	MyPlugin plugin;

	public PlayerEffect(MyPlugin myPlugin) {
		this.plugin = myPlugin;
	}

	@EventHandler
	void DrinkPotion(PlayerItemConsumeEvent event) {
		new FireProtection(plugin, event.getPlayer()).runTaskLater(plugin, 5);
	}

	@EventHandler
	void SplashPotion(PotionSplashEvent event) {

		if (event.getEntity() instanceof Player) {
			new FireProtection(plugin, (Player) event.getEntity()).runTaskLater(plugin, 5);
		}

	}
}