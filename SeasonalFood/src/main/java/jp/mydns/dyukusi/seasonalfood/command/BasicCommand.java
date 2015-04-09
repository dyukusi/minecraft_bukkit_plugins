package jp.mydns.dyukusi.seasonalfood.command;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
import jp.mydns.dyukusi.seasonalfood.seasontype.SeasonType;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BasicCommand implements CommandExecutor {

	SeasonalFood plugin;

	public BasicCommand(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equals("sf")) {

			// check current season
			if (args.length == 0) {
				sender.sendMessage(plugin.get_prefix() + " "
						+ plugin.get_current_season().get_season_color()
						+ plugin.get_current_season().get_inJapanese()
						+ ChatColor.AQUA + " < " + plugin.get_current_season()
						+ " >");
				return true;
			}
			// force next season
			else if (args.length == 1
					&& sender.hasPermission(get_permission_prefix() + "next")) {
				if (args[0].equals("next")) {
					plugin.set_force_next_season(true);
					return true;
				}
			}

		}

		return false;
	}

	private String get_permission_prefix() {
		return "seasonalfood.";
	}

}
