package jp.mydns.dyukusi.myplugin.mycommand;

import jp.mydns.dyukusi.myplugin.MyPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommand implements CommandExecutor {

	MyPlugin plugin;

	public MyCommand(MyPlugin myPlugin) {
		this.plugin = myPlugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equals("mp")) {

			switch (args[0]) {
			case "lban":

				if (args.length == 3) {

					if (plugin.getServer().getPlayer(args[2]) != null) {

						// ban player
						plugin.getServer().dispatchCommand(
								plugin.getServer().getConsoleSender(),
								"ban " + args[2]);

						plugin.getServer().dispatchCommand(
								plugin.getServer().getConsoleSender(),
								"ta tweet " + args[2] + " は、 [" + args[3]
										+ "] という理由でBANされた");

					} else {
						sender.sendMessage(args[2] + " というプレイヤーは存在しません。");
					}

				}

				break;

			default:
				break;
			}

			return true;
		}

		return false;
	}
}
