package jp.mydns.dyukusi.seasonalfood.listener;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
import jp.mydns.dyukusi.seasonalfood.task.BreakStem;
import jp.mydns.dyukusi.seasonalfood.task.Soulsand_to_dirt;

import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class GrowEvent implements Listener {

	SeasonalFood plugin;

	public GrowEvent(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
	}

	@EventHandler
	void BlockGrow(BlockGrowEvent event) {
		Material type = event.getBlock().getType();
		boolean isRipe = false;

		// Melon , Pumpkin
		if (type.equals(Material.AIR)) {
			new BreakStem(plugin, event.getBlock())
					.runTaskAsynchronously(plugin);
			return;
		}
		// Melon_stem , Pumpkin_stem
		else if (type.equals(Material.PUMPKIN_STEM)
				|| type.equals(Material.PUMPKIN_STEM)) {
			return;
		}
		// NetherWarts
		else if (type.equals(Material.NETHER_WARTS)) {
			if (NetherWartsState.RIPE.equals(NetherWartsState.values()[event
					.getNewState().getData().getData()])) {
				isRipe = true;
			}
		}
		// Cocoa
		else if (type.equals(Material.COCOA)) {
			if (event.getNewState().getData().getData() >= 8) {
				isRipe = true;
			}
		}
		// Crops
		else {
			if (CropState.RIPE.equals(CropState.getByData(event.getNewState()
					.getData().getData()))) {
				isRipe = true;
			}
		}

		if (isRipe) {
			Block block = event.getBlock();
			if (plugin.get_isContain_ripe_rate(type)) {
				double rate = plugin.get_ripe_rate(type,
						plugin.get_current_season());
				double random = Math.random();
				// plugin.getServer().broadcastMessage(random + "  vs  " +
				// rate);

				if (random > rate) {
					event.setCancelled(true);
					block.setType(Material.DEAD_BUSH);

					// SoulSand beneath NetherWarts becomes dirt when it ripes.
					if (type.equals(Material.NETHER_WARTS)) {
						new Soulsand_to_dirt(plugin, block).runTaskLater(
								plugin, 20L);
					}
					// normal crops
					else if (Material.CROPS.equals(type)
							|| Material.POTATO.equals(type)
							|| Material.CARROT.equals(type)) {
						plugin.set_dirt_from_soil(block);
					}

				}

			} else {
				plugin.getServer().broadcastMessage(
						plugin.get_prefix() + ChatColor.RED + type + " "
								+ "is not found!");
			}

		}

	}

}
