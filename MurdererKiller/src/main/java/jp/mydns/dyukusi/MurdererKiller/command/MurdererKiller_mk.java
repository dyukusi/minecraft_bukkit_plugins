package jp.mydns.dyukusi.MurdererKiller.command;

import jp.mydns.dyukusi.MurdererKiller.MurdererKiller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MurdererKiller_mk implements CommandExecutor {
	MurdererKiller plugin;

	public MurdererKiller_mk(MurdererKiller p) {
		this.plugin = p;
	}

	// mk�R�}���h
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if ((sender instanceof Player)) {
			Player player = (Player) sender;

			if (args.length == 2) {

				// 投獄コマンド
				if (args[0].equals("jail")) {
					if ((player.getMetadata("killed").get(0).value().equals(args[1]))) {

						if (plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "jail " + args[1])) {
							plugin.getServer().broadcastMessage(
									"§4[MurdererKiller] " + player.getName() + "の要請により、" + args[1] + "は投獄されました。 §b< " + args[1]
											+ " has been jailed for the request from " + player.getName() + " >");
							plugin.getServer().broadcastMessage(
									"§[MurdererKiller] 囚人を釈放するには、Dyukusi[ mail: dyukusin@gmail.com , skype_id: dyukusin ]までご連絡下さい。< In order to release the prisoner, please contact Dyukusi. >");
							player.removeMetadata("killed", plugin);
						}

					} else {
						player.sendMessage("[MurdererKiller] " + args[1] + "に殺害された履歴が見つかりません。");
						return true;
					}

				}

				return true;
			}

		} else {
			sender.sendMessage(ChatColor.RED + "[MurdererKiller] ゲーム内から実行する必要があります。");
		}

		return false;
	}

}
