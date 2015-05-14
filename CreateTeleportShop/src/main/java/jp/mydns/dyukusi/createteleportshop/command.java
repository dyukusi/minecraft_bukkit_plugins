package jp.mydns.dyukusi.createteleportshop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class command implements CommandExecutor {
	CreateTeleportShop plugin;

	public command(CreateTeleportShop cts) {
		this.plugin = cts;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String arg2, String[] args) {

		// プレイヤーからコマンドが実行された
		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
			String cmd = command.getName().toLowerCase();

			if (cmd.equals("cts")) {

				switch (args.length) {
				case 1:
					if (args[0].equals("fare")) {
						player.sendMessage(ChatColor.GREEN + " PortalFare : "
								+ ChatColor.GOLD
								+ plugin.get_fare(player.getLocation()));
						return true;
					}
					break;
				case 2:
					if (args[0].equals("delete")) {

						if (plugin.get_creater_map().containsKey(args[1])) {

							Portal_Information del_portal = plugin
									.get_creater_map().get(args[1]);

							// can delete by portal creator only
							if (del_portal.get_creater_name().equals(
									player.getName())) {

								CommandSender console = plugin.getServer().getConsoleSender();
								StringBuffer del_command = new StringBuffer("wp-portal-delete");
								del_command.append(" "+args[1]);
								
								plugin.getServer().dispatchCommand(console,del_command.toString());
								
								player.performCommand("wp-portal-delete " + args[1]);
								
								player.sendMessage(ChatColor.GREEN+"ポータルの削除に成功しました。"+ChatColor.AQUA+" <Succeeded to delete the portal>");
							} else {
								player.sendMessage(ChatColor.RED
										+ "ポータルの経営者のみ削除することができます。");
								player.sendMessage(ChatColor.AQUA
										+ "<Can delete by the owner of the portal only.>");
							}

						} else {
							player.sendMessage(ChatColor.GOLD
									+ args[1]
									+ ChatColor.RED
									+ " という名前のポータルは存在しません。 /wp-portal-list コマンドで確認して下さい。");
							player.sendMessage(ChatColor.AQUA
									+ "<There isn't the portal. Please check portal name by /wp-portal-list >");
						}

						return true;
					}
					break;
				case 3:
					// コメント登録コマンド
					// cts comment [PORTAL_NAME] [COMMENT]
					if (args[0].equals("comment")) {
						String portal_name = args[1];
						String comment = "";
						for (int i = 2; i < args.length; i++) {
							comment += args[i];
							comment += " ";
						}

						Portal_Information portal;

						if (plugin.get_creater_map().containsKey(portal_name)) {
							portal = plugin.get_creater_map().get(portal_name);

							if (portal.get_creater_name().equals(
									player.getName())) {

								portal.set_comment(comment);
								player.sendMessage(portal_name
										+ " のコメントを更新しました。 " + ChatColor.AQUA
										+ "< The comment of " + ChatColor.WHITE
										+ portal_name + ChatColor.AQUA
										+ " has been updated. >");
							} else {
								player.sendMessage(ChatColor.RED
										+ "ポータル経営者のみがコメント文を変更できます。");
								player.sendMessage(ChatColor.AQUA
										+ "< Must be creater to do this command. >");
							}

							return true;

						} else {
							player.sendMessage(ChatColor.WHITE + portal_name
									+ ChatColor.RED + " という名のポータルは存在しません。 "
									+ ChatColor.AQUA + "< " + ChatColor.WHITE
									+ portal_name + ChatColor.AQUA
									+ " doesn't exist. >");
							return true;
						}

					}
					// 手数料登録コマンド
					// cts charge [PORTAL_NAME] [CHARGE]
					else if (args[0].equals("charge")) {
						String portal_name = args[1];
						int charge;

						try {
							charge = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED
									+ "手数料は整数で設定します。 "
									+ ChatColor.AQUA
									+ "< Please type integer to set the charge. >");
							break;
						}

						if (plugin.get_creater_map().containsKey(portal_name)) {
							// 手数料の設定
							Portal_Information info = plugin.get_creater_map()
									.get(portal_name);

							if (info.get_creater_name()
									.equals(player.getName())) {
								info.set_charge(charge);

								player.sendMessage(ChatColor.WHITE
										+ portal_name + ChatColor.LIGHT_PURPLE
										+ "の手数料が更新されました！ " + ChatColor.AQUA
										+ "< The charge of " + ChatColor.WHITE
										+ portal_name + ChatColor.AQUA
										+ " has been updated! >");
							} else {
								player.sendMessage(ChatColor.RED
										+ "ポータル経営者のみが手数料を変更できます。");
								player.sendMessage(ChatColor.AQUA
										+ "< Must be creater to do this command. >");
							}

						}
						// 指定された名前のポータルが存在しない
						else {
							player.sendMessage(ChatColor.WHITE + portal_name
									+ ChatColor.RED + "という名前のポータルは存在しません。"
									+ ChatColor.WHITE + " /wp-portal-list "
									+ ChatColor.RED + "でポータル名を確認して下さい。 "
									+ ChatColor.AQUA + "< " + ChatColor.WHITE
									+ portal_name + ChatColor.AQUA
									+ " hasn't ever registered. Type "
									+ ChatColor.WHITE + " /wp-portal-list "
									+ ChatColor.AQUA
									+ "to check portals name. >");
						}
						return true;
					}

					break;
				default:
					break;
				}

			}

		} else {
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内からのみ利用可能です。"
					+ ChatColor.AQUA
					+ "< This command is usable in game only.>");
		}

		return false;
	}
}
