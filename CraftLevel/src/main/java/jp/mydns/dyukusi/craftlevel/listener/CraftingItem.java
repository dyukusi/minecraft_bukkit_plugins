package jp.mydns.dyukusi.craftlevel.listener;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.task.GainExperience;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftingItem implements Listener {

	CraftLevel plugin;

	public CraftingItem(CraftLevel p) {
		this.plugin = p;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	void Crafting(CraftItemEvent event) {
		Player creater = (Player) event.getWhoClicked();
		Material material = event.getCurrentItem().getType();
		ItemStack cursor_item = event.getCursor();

		// different material with cursor_item or too many stack
		if ((!material.equals(cursor_item.getType()) && !cursor_item.getType().equals(Material.AIR))
				|| cursor_item.getAmount() >= material.getMaxStackSize()) {
			event.setCancelled(true);
			return;
		}

		if (event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.RIGHT)) {

			String craft_item_name = material.toString();

			CraftingInventory inventory = event.getInventory();
			ItemStack contents[] = inventory.getContents();

			// Failure
			if (Math.random() >= plugin.get_success_rate(plugin.get_player_crafting_level_info(creater).get_level(),
					material)) {
				creater.playSound(creater.getLocation(), Sound.ANVIL_BREAK, 1.2F, 1);
				creater.sendMessage(plugin.get_prefix() + ChatColor.WHITE + " " + craft_item_name + ChatColor.RED
						+ "のクラフトに失敗した!");
				creater.sendMessage(ChatColor.AQUA + " < You failed to craft " + ChatColor.WHITE + craft_item_name
						+ ChatColor.AQUA + ". >");

				event.setCancelled(true);
				// new CraftFailure(plugin,event).runTaskLater(plugin, 40);
				for (int i = 1; i < contents.length; i++) {
					Material item = contents[i].getType();

					if (!item.equals(Material.AIR)) {
						int amount;
						if ((amount = contents[i].getAmount()) > 1) {
							contents[i].setAmount(amount - 1);
						} else {
							inventory.setItem(i, new ItemStack(Material.AIR));
						}
					}
				}

				new GainExperience(plugin, creater, false, event.getRecipe()).runTask(plugin);

			}
			// Success
			else {
				creater.playSound(creater.getLocation(), Sound.ANVIL_USE, 1.2F, 1);
				creater.sendMessage(plugin.get_prefix() + ChatColor.WHITE + " " + craft_item_name + ChatColor.GREEN
						+ "のクラフトに成功した! ");
				creater.sendMessage(ChatColor.AQUA + " < You succeeded to craft " + ChatColor.WHITE + craft_item_name
						+ ChatColor.AQUA + ". >");
				new GainExperience(plugin, creater, true, event.getRecipe()).runTask(plugin);
			}

		}
		// shift craft
		else {
			event.setCancelled(true);
			creater.sendMessage(plugin.get_prefix() + " 現状ではクリックによるクラフトのみ対応しています。");
			creater.sendMessage(ChatColor.AQUA + "< Sorry, Crafting with click is only usable from now. >");
		}

	}

}
