package jp.mydns.dyukusi.seasonalfood.task;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class Soulsand_to_dirt extends BukkitRunnable {

	SeasonalFood plugin;
	Block netherwarts;

	public Soulsand_to_dirt(SeasonalFood seasonalFood, Block nw) {
		this.plugin = seasonalFood;
		this.netherwarts = nw;
	}

	public void run() {
		Location beneath = netherwarts.getLocation();
		beneath = new Location(netherwarts.getWorld(), beneath.getBlockX(), beneath.getBlockY() - 1,
				beneath.getBlockZ());
		Block beneath_block = netherwarts.getWorld().getBlockAt(beneath);

		if (beneath_block.getType().equals(Material.SOUL_SAND)) {
			beneath_block.setType(Material.DIRT);
		}
	}

}
