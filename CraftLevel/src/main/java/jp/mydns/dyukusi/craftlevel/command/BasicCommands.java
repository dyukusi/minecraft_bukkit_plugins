package jp.mydns.dyukusi.craftlevel.command;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BasicCommands implements CommandExecutor {
	CraftLevel plugin;

	public BasicCommands(CraftLevel p) {
		this.plugin = p;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		PlayerCraftLevelData pinf = plugin.get_player_crafting_level_info((Player) sender);

		if (cmd.getName().equals("cl")) {

			if (args.length == 1) {

				// display current level
				if (args[0].equals("level")) {
					sender.sendMessage(plugin.get_prefix() + ChatColor.WHITE + " CraftLevel : " + ChatColor.GOLD
							+ pinf.get_level());
					return true;
				}
				// display current exp
				else if (args[0].equals("exp")) {
					sender.sendMessage(plugin.get_prefix() + ChatColor.WHITE + " Exp : " + ChatColor.GOLD
							+ pinf.get_exp() + ChatColor.WHITE + "/" + plugin.get_next_level_exp()[pinf.get_level()]);
					return true;
				}

			} else if (args.length == 4) {

				// set level Dyukusi 0
				// display current level
				if (args[0].equals("set")) {

					if (plugin.getServer().getPlayer(args[2]) != null) {
						pinf = plugin.get_player_crafting_level_info(plugin.getServer().getPlayer(args[2]));

						// set level
						if (args[1].equals("level")) {
							pinf.set_level(Integer.parseInt(args[3]));
							return true;
						}
						// set exp
						else if (args[1].equals("exp")) {
							pinf.set_exp(Integer.parseInt(args[3]));
							return true;
						}

					} else {
						sender.sendMessage(ChatColor.RED + args[2] + " was not found in this server.");
					}

				}

			}

		}

		return false;
	}

}
