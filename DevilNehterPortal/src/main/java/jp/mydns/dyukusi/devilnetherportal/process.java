package jp.mydns.dyukusi.devilnetherportal;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class process extends BukkitRunnable {

	DevilNetherPortal plugin;

	Chunk center;
	World to;
	int y_from;

	public process(DevilNetherPortal p, World w, Chunk c) {
		plugin = p;
		to = w;
		center = c;
	}

	public void run() {
		LinkedList<Chunk> chunks = new LinkedList<Chunk>();
		int ch_width = 16;

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getWorld().getEnvironment().equals(Environment.NETHER)) {
				player.sendMessage(plugin.get_prefix() + ChatColor.RED + " 不思議な力によって帰路が絶たれた！");
				player.sendMessage(ChatColor.AQUA + "< Mysterious magical power shut the way to the home. >");
			}
		}

		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				chunks.add(to.getChunkAt(center.getX() + x, center.getZ() + z));
			}
		}

		for (Chunk chunk : chunks) {

			for (int x = 0; x < ch_width; x++) {
				for (int z = 0; z < ch_width; z++) {
					for (int y = 0; y < to.getMaxHeight() - 1; y++) {
						Block block = chunk.getBlock(x, y, z);
						Material blockType = block.getType();

						if (blockType.equals(Material.OBSIDIAN)) {
							block.setType(Material.MOSSY_COBBLESTONE);

							if ((int) Math.random() % 2 == 0) {
								block.getWorld().playSound(block.getLocation(), Sound.GHAST_SCREAM, 20, 5F);
							} else {
								block.getWorld().playSound(block.getLocation(), Sound.GHAST_SCREAM2, 20, 5F);
							}
						} else if (blockType.equals(Material.PORTAL)) {
							block.setType(Material.AIR);
						}

					}
				}
			}
		}

	}
}
