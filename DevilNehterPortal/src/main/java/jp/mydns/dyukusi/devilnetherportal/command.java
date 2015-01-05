package jp.mydns.dyukusi.devilnetherportal;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class command implements CommandExecutor {
	DevilNetherPortal plugin;

	public command(DevilNetherPortal p) {
		this.plugin = p;
	}

	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {

		// プレイヤーからコマンドが実行された
		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
			String cmd = command.getName().toLowerCase();

			// ネザーからノーマル世界へ帰還するコマンド。要ブレイズロッドx3
			if (cmd.equals("escape")) {

				// ネザーにいるのか？
				if (player.getWorld().getEnvironment().equals(Environment.NETHER)) {

					// 必要条件を満たしているか？
					if (player.getInventory().containsAtLeast(new ItemStack(Material.BLAZE_ROD), 3)) {

						List<MetadataValue> values = player.getMetadata("from_gate_point");
						from_point to = null;
						for (MetadataValue value : values) {
							if (value.getOwningPlugin().getDescription().getName()
									.equals(plugin.getDescription().getName())) {

								if (value.value() instanceof from_point) {
									to = (from_point) value.value();
								}

							}
						}

						if (to == null) {
							World normal_world = plugin.getServer().getWorld("world");

							if (player.getBedSpawnLocation() != null)
								to = new from_point(normal_world, player.getBedSpawnLocation());
							else {
								to = new from_point(normal_world, normal_world.getSpawnLocation());
							}
						}

						if (to != null) {
							// テレポート処理
							player.teleport(to.location);
							plugin.getServer().broadcastMessage(
									ChatColor.RED + player.getName() + " はネザーから生還した！" + ChatColor.AQUA + " < "
											+ player.getName() + " has survived from the hell! >");
						} else {
							player.sendMessage(ChatColor.RED + "FATAL ERROR : please contact to server owner.");
						}

					} else {
						sender.sendMessage(ChatColor.RED + "脱出には少なくともブレイズロッドが３本必要だ！ " + ChatColor.AQUA
								+ "< At least, you need three blaze rod! >");
					}

				} else {
					player.sendMessage(ChatColor.RED + "escapeコマンドはネザーにいる時のみ利用可能です。" + ChatColor.AQUA
							+ "< Escape command can be used in nether only. >");
					plugin.getServer().broadcastMessage(player.getWorld().toString());
				}
				return true;
			}

		}
		// コンソールからコマンドが実行された
		else {
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内からのみ利用可能です。" + ChatColor.AQUA
					+ "< This command is usable in game only.>");
		}

		return false;
	}
}
