package jp.mydns.dyukusi.weathereffect.process;

import java.util.Random;

import jp.mydns.dyukusi.weathereffect.WeatherEffect;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BreakBlocks extends BukkitRunnable {	
	World world;
	int chunk_width = 16;
	Chunk chunk;
	WeatherEffect plugin;
	

	public BreakBlocks(WeatherEffect p, World w, Chunk c) {
		this.chunk = c;
		this.world = w;
		this.plugin = p;
	}

	// 実際にchunksに対して消火する処理
	@Override
	public void run() {
		Random rand = new Random();

		for (int x = 0; x < chunk_width; x++) {
			for (int z = 0; z < chunk_width; z++) {

				// MAXの高さから
				loopDown: for (int y = chunk.getWorld().getMaxHeight() - 1; y > 0; y--) {
					Block block = chunk.getBlock(x, y, z);
					Material blockType = block.getType();

					switch (blockType) {
					case AIR:
						continue loopDown;

					case JACK_O_LANTERN:
						// 一定確率(50％で消失)
						if ((rand.nextInt(10) % 2) == 0) {
							block.setType(Material.PUMPKIN);
							world.playSound(block.getLocation(), Sound.FIZZ, 20, 1.5F);
						}
						break loopDown;

					case TORCH:
						if ((rand.nextInt(10) % 2) == 0) {														
							block.setType(Material.AIR);
							Item item = world.dropItemNaturally(block.getLocation(), new ItemStack(Material.STICK));
							world.playSound(block.getLocation(), Sound.FIZZ, 20, 1.5F);
						}						
						break loopDown;
					
					case PUMPKIN:
						break;
						
					default:
						break loopDown;
					}

				}

			}
		}

	}
}
