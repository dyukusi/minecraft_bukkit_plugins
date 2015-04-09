package jp.mydns.dyukusi.seasonalfood.task;

import java.util.LinkedList;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class BreakStem extends BukkitRunnable {

	SeasonalFood plugin;
	Block block;

	public BreakStem(SeasonalFood sf, Block block) {
		this.plugin = sf;
		this.block = block;
	}

	public void run() {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Material type = block.getType();

		if (type.equals(Material.PUMPKIN) || type.equals(Material.MELON_BLOCK)) {

			double rate = plugin.get_ripe_rate(type,
					plugin.get_current_season());
			double random = Math.random();

			// failure to ripe
			if (random > rate) {
				// be dead bush from block
				block.setType(Material.AIR);

				Material stem_type = Material.PUMPKIN_STEM;

				if (type.equals(Material.MELON_BLOCK)) {
					stem_type = Material.MELON_STEM;
				}

				LinkedList<Block> break_stems = new LinkedList<Block>();
				break_stems.add(block.getWorld().getBlockAt(
						block.getLocation().add(0, 0, -1)));
				break_stems.add(block.getWorld().getBlockAt(
						block.getLocation().add(1, 0, 0)));
				break_stems.add(block.getWorld().getBlockAt(
						block.getLocation().add(0, 0, 1)));
				break_stems.add(block.getWorld().getBlockAt(
						block.getLocation().add(-1, 0, 0)));

				for (Block stem : break_stems) {
					if (stem.getType().equals(stem_type)) {
						stem.setType(Material.DEAD_BUSH);

						// be dirt from soil
						plugin.set_dirt_from_soil(stem);
					}
				}

			}

		}
	}
}
