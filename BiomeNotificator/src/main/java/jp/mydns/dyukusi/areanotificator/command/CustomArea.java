package jp.mydns.dyukusi.areanotificator.command;

import java.util.List;

import jp.mydns.dyukusi.areanotificator.AreaNotificator;
import jp.mydns.dyukusi.areanotificator.custominfo.CustomAreaInfo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class CustomArea implements CommandExecutor {

	private String cmd_prefix = "an_";
	private AreaNotificator plugin;

	public CustomArea(AreaNotificator biomeNotificator) {
		plugin = biomeNotificator;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (command.getName().equals("an")) {

				// set positions
				if (args.length == 1) {

					if (args[0].equals("first") || args[0].equals("second")) {

						Location location = player.getLocation();
						player.setMetadata(this.cmd_prefix + args[0],
								new FixedMetadataValue(plugin, location));
						sender.sendMessage(plugin.get_prefix()
								+ "Position set to " + location.getBlockX()
								+ "," + location.getBlockY() + ","
								+ location.getBlockZ());
					} else if (args[0].equals("delete")) {

						String area_name = plugin.get_current_area_name(player);

						if (plugin.isCustomArea(area_name)) {
							plugin.remove_custom_area(area_name);
							player.sendMessage(plugin.get_prefix()
									+ ChatColor.YELLOW + area_name
									+ " : Delete completed! ");

						} else {
							player.sendMessage(plugin.get_prefix()
									+ ChatColor.RED
									+ " There isn't custom area here.");
						}

					}

				} else if (args.length == 2) {

					if (args[0].equals("create")) {

						Location first = null, second = null;
						List<MetadataValue> values = player
								.getMetadata(this.cmd_prefix + "first");
						for (MetadataValue value : values) {
							if (value.getOwningPlugin().getDescription()
									.getName()
									.equals(plugin.getDescription().getName())) {
								first = (Location) value.value();
								break;
							}
						}

						values = player.getMetadata(this.cmd_prefix + "second");
						for (MetadataValue value : values) {
							if (value.getOwningPlugin().getDescription()
									.getName()
									.equals(plugin.getDescription().getName())) {
								second = (Location) value.value();
								break;
							}
						}

						if (first == null || second == null) {
							player.sendMessage(plugin.get_prefix()
									+ " Need to set first and second position to create new area.");
						} else {
							plugin.add_new_cutomarea(new CustomAreaInfo(
									args[1], player.getName(), first, second));
							player.sendMessage(plugin.get_prefix()
									+ " Registering new CustomArea is completed!");
						}

					} else if (args[0].equals("delete")) {
						if (plugin.isCustomArea(args[1])) {
							plugin.remove_custom_area(args[1]);
							player.sendMessage(plugin.get_prefix() + " "
									+ args[1] + " was deleted.");

						} else {
							player.sendMessage(plugin.get_prefix() + " "
									+ args[1] + " wast not found.");
						}
						return true;
					}

				}

			}
		}

		// not by Player
		else {
			sender.sendMessage(plugin.get_prefix() + ChatColor.RED
					+ " This command is allowed only by Player.");
			return true;
		}

		return false;
	}
}
