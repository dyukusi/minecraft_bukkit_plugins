package jp.mydns.dyukusi.notificator.command;

import jp.mydns.dyukusi.notificator.Notificator;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerNewsCommand implements CommandExecutor {
	Notificator plugin;
	Economy economy;
	int player_news_char_limit;
	int player_news_charge;

	public PlayerNewsCommand(Notificator notificator, Economy economy, int player_news_char_limit,
			int player_news_charge) {
		this.plugin = notificator;
		this.economy = economy;
		this.player_news_char_limit = player_news_char_limit;
		this.player_news_charge = player_news_charge;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// /noti pnews [PlayerCustomNotification]
		plugin.getServer().broadcastMessage(args.toString());

		if (command.getName().equals("noti")) {

			if (args.length >= 2) {

				// player custom news command
				if (args[0].equals("pnews")) {

					// enough money?
					if (economy.getBalance((OfflinePlayer) sender) >= player_news_charge) {

						StringBuffer message = new StringBuffer("");

						for (int i = 1; i < args.length; i++) {

							if (i != 1)
								message.append(" ");

							message.append(args[i]);
						}

						if (message.length() > player_news_char_limit) {
							sender.sendMessage(ChatColor.RED + Integer.toString(player_news_char_limit)
									+ "文字以下である必要があります。");
							sender.sendMessage(ChatColor.AQUA + "< Need less than "
									+ Integer.toString(player_news_char_limit) + " characters >");
							return true;
						}
						
						//add news to list
						plugin.add_player_news((Player)sender,message.toString());

					} else {
						sender.sendMessage(ChatColor.RED + "所持金が足りません！" + ChatColor.AQUA + " < Not enough money >");
						return true;
					}

				}

			}

		}

		return false;
	}

}
