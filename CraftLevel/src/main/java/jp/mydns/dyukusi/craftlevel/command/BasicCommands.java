package jp.mydns.dyukusi.craftlevel.command;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.task.SavePlayerCLdata;

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

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		PlayerCraftLevelData pinf = null;

		if (sender instanceof Player) {
			pinf = plugin.get_player_crafting_level_info((Player) sender);
		}

		if (cmd.getName().equals("cl")) {

			if (args.length >= 1) {

				// display current level and experience
				if (args[0].equals("status")) {

					if (sender.hasPermission(this.get_permission_prefix()
							+ "status")) {

						if (args.length == 1) {

							// /cl status
							if (pinf != null) {
								sender.sendMessage(this
										.get_display_status_message(pinf));
								return true;
							}
							// excuted from not Player
							else {
								// error message
								sender.sendMessage(this
										.get_must_from_Player_error_message());
							}

						} else if (args.length == 2) {

							Player player;

							// /cl status PlayerName
							if ((player = plugin.getServer()
									.getOfflinePlayer(args[1]).getPlayer()) != null
									&& plugin
											.get_player_crafting_level_info_contains(player)) {
								pinf = plugin
										.get_player_crafting_level_info(plugin
												.getServer()
												.getOfflinePlayer(args[1])
												.getPlayer());
								sender.sendMessage(this
										.get_display_status_message(pinf));
							}
							// cant find player error
							else {
								sender.sendMessage(this
										.get_player_not_found_message(args[1]));
								return true;
							}

						}
					} else {
						sender.sendMessage(this
								.get_no_permission_error_message());
						return true;
					}

				}
				// set current level or exp command
				else if (args[0].equals("set")) {

					if (sender.hasPermission(get_permission_prefix() + "set")) {

						// set level PlayerName amount
						// set exp PlayerName amount
						if (args.length == 4) {

							if (plugin.getServer().getPlayer(args[2]) == null) {
								sender.sendMessage(this
										.get_player_not_found_message(args[2]));
								return false;
							}

							else if (plugin
									.get_player_crafting_level_info_contains(plugin
											.getServer()
											.getOfflinePlayer(args[2])
											.getPlayer())) {
								pinf = plugin
										.get_player_crafting_level_info(plugin
												.getServer()
												.getOfflinePlayer(args[2])
												.getPlayer());

								// set level
								if (args[1].equals("level")) {
									pinf.set_level(Integer.parseInt(args[3]));
								}
								// set exp
								else if (args[1].equals("exp")) {
									pinf.set_exp(Integer.parseInt(args[3]));
								}

								sender.sendMessage(this
										.get_success_set_status_message());
								return true;

							}

						}
					} else {
						sender.sendMessage(this
								.get_no_permission_error_message());
						return true;
					}

				} else if (args[0].equals("save")) {
					plugin.SaveCraftLevelData();
					return true;
				}

			}

		}

		return false;
	}

	String get_no_permission_error_message() {
		return plugin.get_prefix() + ChatColor.RED
				+ " You don't have permission.";
	}

	String get_permission_prefix() {
		return "craftlevel.";
	}

	String get_must_from_Player_error_message() {
		return plugin.get_prefix() + ChatColor.RED
				+ "This command must be excuted by Player.";
	}

	String get_display_status_message(PlayerCraftLevelData pinf) {
		return plugin.get_prefix() + ChatColor.WHITE + " CraftLevel: "
				+ ChatColor.GOLD + pinf.get_level() + ChatColor.WHITE
				+ "  Exp: " + ChatColor.GOLD + pinf.get_exp() + ChatColor.WHITE
				+ "/" + plugin.get_next_level_exp()[pinf.get_level()];
	}

	String get_player_not_found_message(String PlayerName) {
		return plugin.get_prefix() + " " + ChatColor.RED + PlayerName
				+ " is not found in this server.";
	}

	String get_success_set_status_message() {
		return plugin.get_prefix()
				+ "Set status command successfuly completed!";
	}

}
