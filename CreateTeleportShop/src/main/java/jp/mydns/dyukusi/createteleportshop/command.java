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

							portal.set_comment(comment);
							player.sendMessage(portal_name + " のコメントを更新しました。 "
									+ ChatColor.AQUA + "< The comment of "
									+ ChatColor.WHITE + portal_name
									+ ChatColor.AQUA + " has been updated. >");

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
							plugin.get_creater_map().get(portal_name)
									.set_charge(charge);
							player.sendMessage(ChatColor.WHITE + portal_name
									+ ChatColor.LIGHT_PURPLE + "の手数料が更新されました！ "
									+ ChatColor.AQUA + "< The charge of "
									+ ChatColor.WHITE + portal_name
									+ ChatColor.AQUA + " has been updated! >");
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
