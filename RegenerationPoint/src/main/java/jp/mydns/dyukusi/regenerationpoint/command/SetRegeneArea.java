package jp.mydns.dyukusi.regenerationpoint.command;

import jp.mydns.dyukusi.regenerationpoint.RegenerationPoint;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRegeneArea implements CommandExecutor {

	RegenerationPoint plugin;

	public SetRegeneArea(RegenerationPoint regene) {
		this.plugin = regene;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (command.getName().equals("rp")) {
				boolean second_pos = false;

				//create new renege area
				if (args[0].equals("create")) {

					Location first = plugin.get_first_position(), second = plugin.get_second_position();

					if (first == null || second == null) {
						player.sendMessage(" Need to set first and second position to create new area.");
					} else {
						plugin.create_regene_area(first, second);
						player.sendMessage(" Registering new CustomArea is completed!");
					}

				}

				// set positions
				if (args.length == 1) {
					if (!args[0].equals("first")) {
						second_pos = true;
						if (!args[0].equals("second")) {
							return false;
						}
					}

					Location location = player.getLocation();
					plugin.set_position(location, second_pos);
					sender.sendMessage(ChatColor.YELLOW + "Position set to " + location.getBlockX() + ","
							+ location.getBlockY() + "," + location.getBlockZ());

				}

			}
		}
		// not by Player
		else {
			sender.sendMessage(ChatColor.RED + " This command is allowed only by Player.");
			return true;
		}

		return false;
	}

}
