package jp.mydns.dyukusi.craftlevel.task;

import jp.mydns.dyukusi.craftlevel.CraftLevel;

import org.bukkit.Material;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftFailure extends BukkitRunnable {

	CraftLevel plugin;
	CraftItemEvent event;

	public CraftFailure(CraftLevel p, CraftItemEvent e) {
		this.event = e;
		this.plugin = p;
	}

	public void run() {
		CraftingInventory inventory = event.getInventory();
		ItemStack[] contents = inventory.getContents();

		for (int i = 1; i < contents.length - 1; i++) {
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

	}
}
