package jp.mydns.dyukusi.seasonalfood.listener;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BarrenDirt implements Listener {

	SeasonalFood plugin;

	public BarrenDirt(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
	}

	@EventHandler
	void BreakCrops(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material material = event.getBlock().getType();

		// normal crops
		if (Material.CROPS.equals(material) || Material.POTATO.equals(material)
				|| Material.CARROT.equals(material)) {

			// is ripe
			if (CropState.RIPE.equals(CropState.getByData(block.getState()
					.getData().getData()))) {
				plugin.set_dirt_from_soil(block);
			}

		}

	}
}
