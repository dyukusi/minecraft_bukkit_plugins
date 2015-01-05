package jp.mydns.dyukusi.customdurability.command;

import jp.mydns.dyukusi.customdurability.CustomDurability;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetDurabilityCommand implements CommandExecutor {

	CustomDurability plugin;

	public SetDurabilityCommand(CustomDurability customdurability) {
		this.plugin = customdurability;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equals("cd")) {

			if (args.length == 2) {
				if (args[0].equals("set")) {
					Player player = (Player) sender;
					ItemStack hand_item = player.getItemInHand();
					// player.sendMessage(hand_item.getType().name()+"を持っています。");
					short new_durability = Short.parseShort(args[1]);

					hand_item.setDurability(new_durability);
					player.setItemInHand(hand_item);
					player.sendMessage(plugin.get_prefix() + ChatColor.WHITE + hand_item.getType().name()
							+ " durability was set to " + new_durability);

					return true;

				}
			}
		}

		return false;
	}
}
