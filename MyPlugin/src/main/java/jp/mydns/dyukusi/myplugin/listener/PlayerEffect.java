package jp.mydns.dyukusi.myplugin.listener;

import jp.mydns.dyukusi.myplugin.MyPlugin;
import jp.mydns.dyukusi.myplugin.task.FireProtection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

	@EventHandler
	void CraftItem(CraftItemEvent event) {

		plugin.getServer().broadcastMessage(ChatColor.GREEN + "item name : " + event.getCurrentItem().getType());

		Material type = event.getCurrentItem().getType();
		
		if(type.equals(Material.STONE_SWORD)){
			ItemStack item = event.getCurrentItem();
			ItemMeta meta = item.getItemMeta();
			
			
		}
		
	}

}