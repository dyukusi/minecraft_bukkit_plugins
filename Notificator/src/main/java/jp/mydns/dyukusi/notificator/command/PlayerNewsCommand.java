package jp.mydns.dyukusi.notificator.command;

import jp.mydns.dyukusi.notificator.Notificator;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerNewsCommand implements CommandExecutor {
	Notificator plugin;
	Economy economy;
	int player_news_char_limit;
	int player_news_charge;

	public PlayerNewsCommand(Notificator notificator, Economy economy,
			int player_news_char_limit, int player_news_charge) {
		this.plugin = notificator;
		this.economy = economy;
		this.player_news_char_limit = player_news_char_limit;
		this.player_news_charge = player_news_charge;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equals("noti")) {

			if (args.length >= 1) {

				// player custom news command
				if (args[0].equals("pnews")) {

					if (args.length == 2) {

						// display player news history
						try {
							Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor.RED
									+ "must be integer to select page.");
							return true;
						}

						int display_limit = 5;

						int page = Integer.parseInt(args[1]) - 1;
						int max_page = (int) Math.ceil((double) plugin
								.get_player_news().size()
								/ (double) display_limit);

						if (page <= -1 || max_page < page + 1) {
							sender.sendMessage(ChatColor.RED
									+ "Invalid page number.");
							return true;
						}

						int base_page = display_limit * page;

						sender.sendMessage("-----------Player News( "
								+ (page + 1) + "/" + max_page + " )-----------");

						for (int i = base_page; i < plugin.get_player_news()
								.size() && i < base_page + display_limit; i++) {
							sender.sendMessage(ChatColor.AQUA
									+ "("
									+ i
									+ ") "
									+ plugin.get_player_news().get(i)
											.toString());
						}

						return true;

					} else if (args.length >= 3) {

						if (args[1].equals("add")) {

							if (sender.hasPermission(this
									.get_permission_prefix() + "add")) {

								// enough money?
								if (economy.getBalance((OfflinePlayer) sender) >= player_news_charge) {

									// withdraw from sender's account
									economy.withdrawPlayer(
											(OfflinePlayer) sender,
											player_news_charge);

									StringBuffer message = new StringBuffer("");

									for (int i = 2; i < args.length; i++) {

										if (i != 1)
											message.append(" ");

										message.append(args[i]);
									}

									if (message.length() > player_news_char_limit) {
										sender.sendMessage(ChatColor.RED
												+ Integer
														.toString(player_news_char_limit)
												+ "文字以下である必要があります。");
										sender.sendMessage(ChatColor.AQUA
												+ "< Need less than "
												+ Integer
														.toString(player_news_char_limit)
												+ " characters >");
										return true;
									}

									// add news to list
									plugin.add_player_news(
											plugin.get_current_time(),
											sender.getName(),
											message.toString(), true);

									// Tweet
									plugin.getServer().dispatchCommand(
											plugin.getServer()
													.getConsoleSender(),
											"ta tweet " + sender.getName()
													+ "「" + message.toString()
													+ "」");

									return true;

								} else {
									sender.sendMessage(ChatColor.RED
											+ "所持金が足りません！" + ChatColor.AQUA
											+ " < Not enough money >");
									return true;
								}
							} else if (args[1].equals("delete")
									&& sender
											.hasPermission(this
													.get_permission_prefix()
													+ "delete")) {

								try {
									Integer.parseInt(args[2]);
								} catch (NumberFormatException e) {
									plugin.display_help(sender);
									sender.sendMessage(ChatColor.RED
											+ "Must be integer to select message index.");
									return true;
								}

								int index = Integer.parseInt(args[2]);

								if (plugin.get_player_news().size() < index + 1) {
									plugin.display_help(sender);
									sender.sendMessage(ChatColor.RED
											+ "Invalid index number.");
									return true;
								}

								plugin.get_player_news().remove(index);
								sender.sendMessage(ChatColor.GREEN
										+ "Delete player message " + index
										+ " completed.");
								return true;

							}

						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "You don't have permission.");
						return true;
					}

				}

			}

		}

		plugin.display_help(sender);
		return true;
	}

	private String get_permission_prefix() {
		return "notificator.";
	}
}
