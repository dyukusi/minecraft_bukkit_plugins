package jp.mydns.dyukusi.seasonalfood.listener;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
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

		// NetherWarts
		if (type.equals(Material.NETHER_WARTS)) {
			if (NetherWartsState.RIPE.equals(NetherWartsState.values()[event.getNewState().getData().getData()])) {
				isRipe = true;
			}
		}
		// Crops
		else {
			if (CropState.RIPE.equals(CropState.getByData(event.getNewState().getData().getData()))) {
				isRipe = true;
			}
		}

		if (isRipe) {
			Block block = event.getBlock();
			if (plugin.get_isContain_ripe_rate(type)) {
				double rate = plugin.get_ripe_rate(type, plugin.get_current_season());
				double random = Math.random();
				// plugin.getServer().broadcastMessage(random + "  vs  " +
				// rate);

				if (random > rate) {
					event.setCancelled(true);
					block.setType(Material.DEAD_BUSH);

					// SoulSand beneath NetherWarts becomes dirt when it ripes.
					if (type.equals(Material.NETHER_WARTS)) {
						new Soulsand_to_dirt(plugin, block).runTaskLater(plugin, 20L);
					}
				}

			} else {
				plugin.getServer().broadcastMessage(plugin.get_prefix() + ChatColor.RED + " " + "is not found!");
			}

		}

	}

	@EventHandler
	void LeavesDecay(LeavesDecayEvent event) {
		plugin.getServer().broadcastMessage(event.getBlock().getType() + " is decaying");
	}

	@EventHandler
	void BlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.DEAD_BUSH)) {
			plugin.getServer().broadcastMessage("枯れた草消失確認。キャンセルを試みます。");
			event.setCancelled(true);
		}
	}

	@EventHandler
	void BlockFade(BlockFadeEvent event) {
		plugin.getServer().broadcastMessage(event.getBlock().getType() + " is fading");

	}

}
