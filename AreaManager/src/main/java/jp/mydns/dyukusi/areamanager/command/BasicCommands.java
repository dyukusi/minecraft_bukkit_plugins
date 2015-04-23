package jp.mydns.dyukusi.areamanager.command;

import java.util.List;
import java.util.Map.Entry;

import jp.mydns.dyukusi.areamanager.AreaManager;
import jp.mydns.dyukusi.areamanager.areainfo.AreaInformation;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.sk89q.worldedit.Countable;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class BasicCommands implements CommandExecutor {

	private String cmd_prefix = "am_";
	AreaManager plugin;

	public BasicCommands(AreaManager areaManager) {
		plugin = areaManager;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (command.getName().equals("am")) {

				// set positions
				if (args.length == 1) {

					if ((args[0].equals("first") || args[0].equals("second"))
							&& sender.hasPermission(get_permission_prefix()
									+ "set")) {

						Location location = player.getLocation();
						player.setMetadata(this.cmd_prefix + args[0],
								new FixedMetadataValue(plugin, location));
						sender.sendMessage(plugin.get_prefix()
								+ "Position set to " + location.getBlockX()
								+ "," + location.getBlockY() + ","
								+ location.getBlockZ());
						return true;

					} else if (args[0].equals("delete")
							&& sender.hasPermission(get_permission_prefix()
									+ "delete")) {

						String area_name = plugin.get_current_area_name(player);

						if (plugin.isRegisteredArea(area_name)) {
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
					// display all custom area
					else if (args[0].equals("list")
							&& sender.hasPermission(get_permission_prefix()
									+ "list")) {
						player.sendMessage(ChatColor.YELLOW
								+ "----- CustomArea -----");
						for (Entry<String, AreaInformation> ent : plugin
								.get_areainfo_map().entrySet()) {
							AreaInformation info = ent.getValue();
							Location[] loc = info.get_location();

							String AreaName;

							if (!info.get_custom_area_name().equals("null")) {
								AreaName = info.get_custom_area_name();
							} else {
								AreaName = info.get_area_name();
							}

							player.sendMessage(ChatColor.GOLD + AreaName
									+ ChatColor.WHITE + " : "
									+ info.get_range_str() + " , "
									+ info.get_owner_name());
						}

						return true;
					}
					// buy land
					else if (args[0].equals("buy")
							&& sender.hasPermission(get_permission_prefix()
									+ "buy")) {
						if (!plugin.get_current_area_name(player)
								.equals("null")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));

							// can not buy own land
							if (info.get_owner_name().equals(player.getName())) {
								player.sendMessage(ChatColor.RED
										+ "既に所有している土地を購入することはできません。");
								player.sendMessage(ChatColor.AQUA
										+ "< Can not buy own land. >");

								return true;
							}

							if (info.get_can_buy()) {

								int current_money = (int) plugin.get_economy()
										.getBalance(player);

								if (current_money >= info.get_price()) {

									ProtectedRegion region = plugin
											.get_wg()
											.getRegionManager(
													info.get_location()[0]
															.getWorld())
											.getRegion(info.get_area_name());

									// count own lands
									int ownland = 0;
									for (AreaInformation area : plugin
											.get_areainfo_map().values()) {
										if (area.get_owner_name().equals(
												player.getName()))
											ownland++;
									}

									if (ownland <= 2) {

										plugin.get_economy().withdrawPlayer(
												player, info.get_price());

										if (!info.get_owner_name().equals(
												"none")) {
											player.performCommand("od deposit "
													+ info.get_owner_name()
													+ " " + info.get_price()
													+ " " + "土地買収"
													+ ChatColor.AQUA
													+ " <Purchase your land> ");
										}

										info.buy_land(player, region);

										// set metadata
										player.setMetadata("BuyLand",
												new FixedMetadataValue(plugin,
														true));

										player.sendMessage(ChatColor.GREEN
												+ "おめでとうございます！土地の購入が完了しました。");
										player.sendMessage(ChatColor.AQUA
												+ "< Congraturations!! Now you are the owner of this land. >");
									} else {
										player.sendMessage(ChatColor.RED
												+ "所持金が足りません。" + ChatColor.AQUA
												+ " <Not enough money>");
									}
									
								}
								//having too many lands
								else{
									player.sendMessage(ChatColor.RED+"土地は一人２箇所まで所有することが可能です。");
									player.sendMessage(ChatColor.AQUA+"< You can't have three or more lands. >");									
								}
								
								return true;

							}
							// can not buy the land
							else {
								player.sendMessage(ChatColor.RED
										+ "この土地を購入することは出来ません。");
								player.sendMessage(ChatColor.AQUA
										+ "< Can not buy this land now. >");
							}

						} else {
							player.sendMessage(ChatColor.RED
									+ "Need to be in an area.");
						}

						return true;
					} else if (args[0].equals("sell")
							&& sender.hasPermission(get_permission_prefix()
									+ "sell")) {
						if (!plugin.get_current_area_name(player)
								.equals("null")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));

							if (info.get_can_buy()) {
								info.set_owner_want_to_sell(false);
								player.sendMessage(ChatColor.GREEN
										+ "Now not on sale.");
							} else {
								info.set_owner_want_to_sell(true);
								player.sendMessage(ChatColor.GREEN
										+ "Now on sale for other player.");
							}

						} else {
							player.sendMessage(ChatColor.RED
									+ "Need to be in an area.");
						}
						return true;
					}
					// hide own area auto info
					else if (args[0].equals("hide")
							&& sender.hasPermission(get_permission_prefix()
									+ "hide")) {

						// hide
						if (!player.hasMetadata("hide_own_area_info")) {
							player.setMetadata("hide_own_area_info",
									new FixedMetadataValue(plugin, true));

							player.sendMessage(ChatColor.GREEN
									+ "所有地の自動情報表示機能を" + ChatColor.GOLD + "OFF"
									+ ChatColor.GREEN + "にしました。");
							player.sendMessage(ChatColor.AQUA
									+ "Disable auto info display function at your own area.");

						}
						// display
						else {
							player.removeMetadata("hide_own_area_info", plugin);
							player.sendMessage(ChatColor.GREEN
									+ "所有地の自動情報表示機能を" + ChatColor.GOLD + "ON"
									+ ChatColor.GREEN + "にしました。");
							player.sendMessage(ChatColor.AQUA
									+ "Enable auto info display function at your own area.");
						}

						return true;

					}
					// list all lands
					else if (args[0].equals("list")
							&& sender.hasPermission("list")) {

					}

				} else if (args.length == 2) {

					if (args[0].equals("create")
							&& sender.hasPermission(get_permission_prefix()
									+ "create")) {

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

							// expand vert
							first.setY(0);
							second.setY(255);

							plugin.add_new_area(new AreaInformation(args[1],
									"null", "none", 0, 0, first, second, false,
									true, plugin.get_current_hour_time()));

							ProtectedCuboidRegion new_area = new ProtectedCuboidRegion(
									args[1],
									AreaManager.convertToSk89qBV(first),
									AreaManager.convertToSk89qBV(second));

							new_area.setFlag(DefaultFlag.PASSTHROUGH,
									State.DENY);

							plugin.get_wg().getRegionManager(first.getWorld())
									.addRegion(new_area);

							;

							player.sendMessage(plugin.get_prefix()
									+ " Registering new area is completed!");
						}

					}
					// delete area
					else if (args[0].equals("delete")
							&& sender.hasPermission(get_permission_prefix()
									+ "delete")) {
						if (plugin.isRegisteredArea(args[1])) {
							plugin.remove_custom_area(args[1]);
							player.sendMessage(plugin.get_prefix() + " "
									+ args[1] + " was deleted.");

						} else {
							player.sendMessage(plugin.get_prefix() + " "
									+ args[1] + " wast not found.");
						}
						return true;
					} else if (args[0].equals("ignorey")
							&& sender.hasPermission(get_permission_prefix()
									+ "ignorey")) {
						if (plugin.isRegisteredArea(args[1])) {

							AreaInformation info = plugin
									.get_area_info(args[1]);

							// true -> false
							if (info.get_ignore_y()) {
								info.set_ignore_y(false);
								player.sendMessage(plugin.get_prefix()
										+ ChatColor.GOLD + args[1]
										+ ChatColor.GREEN
										+ " : Now not ignoring y.");
							}
							// false -> true
							else {
								info.set_ignore_y(true);
								player.sendMessage(plugin.get_prefix()
										+ ChatColor.GOLD + args[1]
										+ ChatColor.GREEN
										+ " : Now ignoring y.");
							}

						} else {
							player.sendMessage(plugin.get_prefix() + " "
									+ args[1] + " wast not found.");
						}
						return true;
					}
					// reset owner
					else if (args[0].equals("owner")
							&& args[1].equals("reset")
							&& sender.hasPermission(get_permission_prefix()
									+ "reset")) {

						if (!plugin.get_current_area_name(player)
								.equals("null")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));

							info.reset_owner();
							player.sendMessage(ChatColor.GREEN
									+ "ResetOwner process have been completed!");

						} else {
							player.sendMessage("Need to be in Area.");
							return true;
						}

					} else if (args[0].equals("rename")) {
						if (!plugin.get_current_area_name(player)
								.equals("null")
								&& sender.hasPermission(get_permission_prefix()
										+ "rename")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));
							info.set_custom_area_name(args[1]);
							player.sendMessage(ChatColor.GREEN
									+ "Rename completed!");
							return true;
						}
					}
					// set owner price command
					else if (args[0].equals("price")
							&& sender.hasPermission(get_permission_prefix()
									+ "price")) {

						try {
							Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED
									+ "Price amount must be integer.");
							return true;
						}

						int new_price = Integer.parseInt(args[1]);

						if (!plugin.get_current_area_name(player)
								.equals("null")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));

							if (info.get_owner_name().equals(player.getName())) {
								info.set_price(new_price);
								player.sendMessage("Land price change have been completed!");
							} else {
								player.sendMessage(ChatColor.RED
										+ "土地のオーナーのみが価格を変更できます。");
								player.sendMessage(ChatColor.AQUA
										+ "< Must be owner to do this command. >");
							}

							return true;
						}
					}
					// set initial price command
					else if (args[0].equals("iniprice")
							&& sender.hasPermission(get_permission_prefix()
									+ "iniprice")) {
						try {
							Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED
									+ "Initial price amount must be integer.");
							return true;
						}

						int new_price = Integer.parseInt(args[1]);

						if (!plugin.get_current_area_name(player)
								.equals("null")) {
							AreaInformation info = plugin.get_area_info(plugin
									.get_current_area_name(player));
							info.set_initial_price(new_price);
							player.sendMessage("Land initial price change has been completed!");
							return true;
						}
					}

				}

			}
		}

		return false;
	}

	private String get_permission_prefix() {
		return "areamanager.";
	}

}
