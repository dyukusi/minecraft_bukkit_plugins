package jp.mydns.dyukusi.invasion.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.custommonster.CustomEntitySkeleton;
import jp.mydns.dyukusi.invasion.custommonster_base.CustomEntityType;
import jp.mydns.dyukusi.invasion.custommonster_base.getPfield_registerEnt;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;
import jp.mydns.dyukusi.invasion.task.Check_spawnloc_task;
import jp.mydns.dyukusi.invasion.task.invasion_task;
import jp.mydns.dyukusi.invasion.task.load_all_invasion_task;
import jp.mydns.dyukusi.invasion.task.save_invation_data_task;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class InvasionCommands implements CommandExecutor {

	Invasion plugin;
	Map<String, InvasionData> inv_data;

	public InvasionCommands(Invasion plugin, Map<String, InvasionData> inv_data) {
		this.plugin = plugin;
		this.inv_data = inv_data;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;
			InvasionData invd = null;

			if (command.getName().equals("inv")) {

				if (args[0].equals("create")) {
					plugin.create_new_inv_data(args[1]);
					player.sendMessage(ChatColor.GREEN
							+ "Create new InvasionData has completed!");
					return true;

				}
				// create new InvasionData command
				else {

					if (this.inv_data.containsKey(args[0])) {
						invd = this.inv_data.get(args[0]);

					} else {
						player.sendMessage(args[0] + ChatColor.RED
								+ " was not found.");
						return true;
					}

				}

				switch (args[1]) {
				case "check":

					new Check_spawnloc_task(plugin, player, invd, new Location(
							Bukkit.getWorld("world"), 1701, 10, 1263))
							.runTaskAsynchronously(plugin);
					player.sendMessage("Teleport spawn point process has completed.");

					break;

				case "start":
					player.sendMessage(ChatColor.GREEN + args[0]
							+ " invasion started!");
					new invasion_task(plugin, invd)
							.runTaskAsynchronously(plugin);

					break;

				case "register":

					player.sendMessage("Register custom entities!");

					getPfield_registerEnt.registerEntities();

					return true;

				case "spawn":

					player.sendMessage("Spawn custom monster at your location.");

					Location location = player.getLocation();

					World world = ((CraftWorld) location.getWorld())
							.getHandle();

					List<Location> dest_list = new ArrayList<Location>();
					dest_list
							.add(new Location(player.getWorld(), -208, 85, 197));
					dest_list
							.add(new Location(player.getWorld(), -219, 85, 271));

					EntityInsentient entity = new CustomEntitySkeleton(world,
							dest_list);

					// equip
					ItemStack testSword = CraftItemStack
							.asNMSCopy(new org.bukkit.inventory.ItemStack(
									Material.BOW, 1));
					org.bukkit.inventory.ItemStack testSwordItem = CraftItemStack
							.asCraftMirror(testSword);

					entity.setEquipment(0, testSword);

					entity.setPositionRotation(location.getX(),
							location.getY(), location.getZ(),
							location.getYaw(), location.getPitch());

					// entity.prepare(null,null);
					world.addEntity(entity, SpawnReason.CUSTOM);
					entity.p(entity);

					return true;

				case "save":

					new save_invation_data_task(plugin, invd,
							plugin.get_inv_data_path() + "/"
									+ invd.get_invasion_name() + ".yml")
							.runTask(plugin);

					plugin.getServer().broadcastMessage(
							ChatColor.GREEN + "Save " + ChatColor.GOLD
									+ invd.get_invasion_name()
									+ ChatColor.GREEN
									+ " process has completed!");

					return true;

				case "load":

					new load_all_invasion_task(plugin, invd).runTask(plugin);
					plugin.getServer()
							.broadcastMessage(
									ChatColor.GREEN
											+ "Load all invasion task process has completed!");
					return true;

				case "set":

					switch (args[2]) {

					case "spawnloc":

						invd.add_spawn_check_location(player.getLocation());

						plugin.getServer().broadcastMessage(
								ChatColor.GREEN
										+ "Added new spawn check location to "
										+ invd.get_invasion_name());

						break;

					case "monster":

						// how to use
						if (args.length == 3) {
							player.sendMessage(ChatColor.LIGHT_PURPLE + "/inv "
									+ invd.get_invasion_name()
									+ " set monster [MonsterName] [Health] "
									+ "[MoveSpeed] [JumpLevel]");
						}
						// add new monster at your location with specified
						// options
						else {

							CustomEntityType monster = CustomEntityType
									.valueOf(args[3]);
							int health = Integer.parseInt(args[4]);
							double move_speed = Integer.parseInt(args[5]);
							int jump_lv = Integer.parseInt(args[6]);

							invd.add_monster(new InvMonsterData(monster, player
									.getLocation(), health, move_speed, jump_lv));

							player.sendMessage(ChatColor.GREEN
									+ "Add new monster has completed!");
						}

						break;

					case "destination":
						invd.add_destination(player.getLocation());
						player.sendMessage(ChatColor.GREEN
								+ "Adding new monster destination process has completed!");

						break;

					case "duration":

						int dur = Integer.parseInt(args[3]);

						invd.set_duration(dur);

						player.sendMessage(ChatColor.GREEN
								+ "Invasion duration is now set to" + dur);

						break;

					default:
						break;
					}

					return true;

				default:
					break;
				}

			}

		}

		return false;
	}
}
