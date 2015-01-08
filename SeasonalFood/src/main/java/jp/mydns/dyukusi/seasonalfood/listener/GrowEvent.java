package jp.mydns.dyukusi.seasonalfood.listener;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.messaging.PluginChannelDirection;

public class GrowEvent implements Listener {

	SeasonalFood plugin;

	public GrowEvent(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
	}

	@EventHandler
	void BlockGrow(BlockGrowEvent event) {

		// isRIPE or not
		if (CropState.RIPE.equals(CropState.getByData(event.getNewState().getData().getData()))) {
			Block block = event.getBlock();
			Material material = block.getType();

			if (plugin.get_isContain_ripe_rate(material)) {
				double rate = plugin.get_ripe_rate(material, plugin.get_current_season());
				double random = Math.random();
//				plugin.getServer().broadcastMessage(random + "  vs  " + rate);

				if (random > rate) {
					event.setCancelled(true);
					block.setType(Material.DEAD_BUSH);
				}

			} else {
				plugin.getServer().broadcastMessage(
						plugin.get_prefix() + ChatColor.RED + " " + material + "is not found!");
			}
		}

	}
}
