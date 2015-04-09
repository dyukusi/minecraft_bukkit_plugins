package jp.mydns.dyukusi.myplugin.mycommand;

import jp.mydns.dyukusi.myplugin.MyPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaySomething implements CommandExecutor {

	MyPlugin plugin;

	public SaySomething(MyPlugin myPlugin) {
		this.plugin = myPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		Player player = plugin.getServer().getOfflinePlayer("Dyukusi").getPlayer();

		if (command.getName().equals("s")) {

			StringBuffer say = new StringBuffer();

			for (String str : args) {
				say.append(str + " ");
			}

			player.chat(say.toString());
			return true;
		}

		return false;
	}

}
