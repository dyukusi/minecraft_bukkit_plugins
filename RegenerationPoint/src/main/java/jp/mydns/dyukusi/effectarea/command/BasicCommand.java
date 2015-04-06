package jp.mydns.dyukusi.effectarea.command;

import jp.mydns.dyukusi.effectarea.EffectArea;
import jp.mydns.dyukusi.effectarea.regeneinfo.EffectAreaInfo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BasicCommand implements CommandExecutor {

	EffectArea plugin;

	public BasicCommand(EffectArea effectarea) {
		this.plugin = effectarea;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (sender.hasPermission(this.get_permission_prefix()
					+ "areaconfigure")) {

				if (command.getName().equals("ea")) {
					boolean second_pos = false;

					// set positions
					if (args.length == 1) {
						if (!args[0].equals("first")) {
							second_pos = true;
							if (!args[0].equals("second")) {
								return false;
							}
						}

						Location location = player.getLocation();
						plugin.set_position(location, second_pos);
						sender.sendMessage(ChatColor.YELLOW
								+ "Position set to " + location.getBlockX()
								+ "," + location.getBlockY() + ","
								+ location.getBlockZ());
						return true;

					} else if (args.length == 2) {
						// create new effect area
						if (args[0].equals("create")) {

							Location first = plugin.get_first_position(), second = plugin
									.get_second_position();

							if (first == null || second == null) {
								player.sendMessage(" Need to set first and second position to create new area.");
							} else {
								plugin.add_effect_area(args[1], first, second);
							}
							return true;

						}
						// remove effect area
						else if (args[0].equals("delete")) {
							if (plugin.isEffect_area(args[1])) {
								plugin.remove_effect_area(args[1]);
								player.sendMessage("Removing " + args[1]
										+ " completed!");
							} else {
								player.sendMessage(args[1] + " was not found.");
							}

							return true;
						}

					}
					// set walking speed
					else if (args.length == 4) {

						if (args[0].equals("set")) {

							if (args[1].equals("speed")) {

								if (plugin.isEffect_area(args[2])) {
									EffectAreaInfo area_info = plugin
											.get_effect_area_info(args[2]);

									try {
										Float.parseFloat(args[3]);
									} catch (NumberFormatException e) {
										player.sendMessage("Invalid format");
										return true;
									}

									float speed = Float.parseFloat(args[3]);

									if (speed < 0 || 1.0F < speed) {
										player.sendMessage("speed must be between 0.0 and 1.0");
										return true;
									}

									area_info.set_walking_speed(speed);
									player.sendMessage("Setting speed completed!");
									return true;

								}

							}
							// food regeneration speed
							else if (args[1].equals("hunger")) {

								if (plugin.isEffect_area(args[2])) {
									EffectAreaInfo area_info = plugin
											.get_effect_area_info(args[2]);

									try {
										Integer.parseInt(args[3]);
									} catch (NumberFormatException e) {
										player.sendMessage("Invalid format");
										return true;
									}

									int food_regene = Integer.parseInt(args[3]);

									area_info
											.set_hunger_regene_speed(food_regene);
									player.sendMessage("Setting hunger regene speed completed!");
									return true;

								}

							} else if (args[1].equals("spawn")) {

								if (plugin.isEffect_area(args[2])) {
									EffectAreaInfo area_info = plugin
											.get_effect_area_info(args[2]);

									// on
									if (area_info.set_monsterspawn_toggle()) {
										player.sendMessage("Monster spawn : on");
									}
									// off
									else {
										player.sendMessage("Monster spawn : off");
									}

									return true;
								}
							}

						}

					}
					// add effect to players in custom area
					// /bn effect [Area_name] [Effect_name] [level] [duration]
					else if (args.length == 5) {

						if (args[0].equals("effect")) {

							if (plugin.isEffect_area(args[1])) {
								EffectAreaInfo info = plugin
										.get_effect_area_info(args[1]);

								if (PotionEffectType.getByName(args[2]) != null) {
									String effect_name = args[2];

									try {
										Integer.parseInt(args[3]);
										Integer.parseInt(args[4]);
									} catch (NumberFormatException e) {
										player.sendMessage("Invalid format");
										return false;
									}

									int level = Integer.parseInt(args[3]);
									int duration = Integer.parseInt(args[4]);

									// add new effect
									info.add_effect(effect_name, level,
											duration);

									player.sendMessage(ChatColor.GREEN
											+ " Adding new effect completed!");

									return true;
								}

							}

						}

					}

				}
			}
		}
		// not by Player
		else {
			sender.sendMessage(ChatColor.RED
					+ " This command is allowed only by Player.");
			return true;
		}

		return false;
	}

	private String get_permission_prefix() {
		return "effectarea.";
	}
}
