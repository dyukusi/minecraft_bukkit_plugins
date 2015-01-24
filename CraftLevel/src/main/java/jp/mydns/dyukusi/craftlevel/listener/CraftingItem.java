package jp.mydns.dyukusi.craftlevel.listener;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.config.Message;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.materialinfo.MaterialInfo;
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
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftingItem implements Listener {

	CraftLevel plugin;

	public CraftingItem(CraftLevel p) {
		this.plugin = p;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	void Crafting(CraftItemEvent event) {
		Player creater = (Player) event.getWhoClicked();
		PlayerCraftLevelData pinfo = plugin
				.get_player_crafting_level_info(creater);
		MaterialInfo material_info = CraftLevel.get_material_info(event
				.getCurrentItem().getType());
		ItemStack cursor_item = event.getCursor();

		// different material with cursor_item or too many stack
		if ((!material_info.get_material().equals(cursor_item.getType()) && !cursor_item
				.getType().equals(Material.AIR))
				|| cursor_item.getAmount()
						+ event.getRecipe().getResult().getAmount() > material_info
						.get_material().getMaxStackSize()) {
			event.setCancelled(true);
			return;
		}

		if (event.getClick().equals(ClickType.LEFT)
				|| event.getClick().equals(ClickType.RIGHT)) {

			String craft_item_name = material_info.get_material().name();

			CraftingInventory inventory = event.getInventory();
			ItemStack contents[] = inventory.getContents();

			// Failure
			if (Math.random() >= material_info.get_success_rate(pinfo
					.get_level())) {

				creater.playSound(creater.getLocation(), Sound.ANVIL_BREAK,
						1.2F, 1);

				creater.sendMessage(Message.Craft_Failure
						.get_message(craft_item_name));

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

				new GainExperience(plugin, creater, false, event.getRecipe())
						.runTask(plugin);

			}
			// Success
			else {
				creater.playSound(creater.getLocation(), Sound.ANVIL_USE, 1.2F,
						1);

				creater.sendMessage(Message.Craft_Success
						.get_message(craft_item_name));
			
				new GainExperience(plugin, creater, true, event.getRecipe())
						.runTask(plugin);
			}

		}
		// shift craft
		else {
			event.setCancelled(true);
			creater.sendMessage(Message.Error_Shift.get_message());
		}

	}

}
