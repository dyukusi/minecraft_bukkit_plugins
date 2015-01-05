package jp.mydns.dyukusi.listener;

import jp.mydns.dyukusi.customdurability.CustomDurability;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

	CustomDurability plugin;

	public BlockBreakListener(CustomDurability customDurability) {
		this.plugin = customDurability;
	}

	@EventHandler
	void BlockBreak(BlockBreakEvent event) {
		// Block block = event.getBlock();
		Player player = event.getPlayer();
		ItemStack hand_item = player.getItemInHand();
		Material block_type = event.getBlock().getType();
		int decrease;

		if ((decrease = plugin.get_decrease(hand_item.getType(), block_type)) >= 0) {
			double unbreaking_probability = plugin.get_unbreaking_effect(hand_item
					.getEnchantmentLevel(Enchantment.DURABILITY));
			double random = Math.random();
			player.sendMessage(random + " :: " + unbreaking_probability);

			// undamage
			if (random < unbreaking_probability) {
				player.sendMessage("耐久エンチャント発動！");
				decrease = 0;
			} else {
				player.sendMessage(decrease + "のダメージ！");
			}
			

			hand_item.setDurability((short) (hand_item.getDurability() + decrease));
			player.setItemInHand(hand_item);
		}

	}

}
