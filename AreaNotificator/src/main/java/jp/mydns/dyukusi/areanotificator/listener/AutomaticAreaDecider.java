package jp.mydns.dyukusi.areanotificator.listener;

import jp.mydns.dyukusi.areanotificator.AreaNotificator;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class AutomaticAreaDecider implements Listener {

	AreaNotificator plugin;

	public AutomaticAreaDecider(AreaNotificator areaNotificator) {
		this.plugin = areaNotificator;
	}

	@EventHandler
	void EntitySpawn(CreatureSpawnEvent event) {
	
		// elder guardian -> ocean monument
		if (event.getEntityType().equals(EntityType.GUARDIAN)) {

			Guardian guardian = (Guardian) event.getEntity();

			// elder guardian
			if (guardian.isElder()) {
				Location location = guardian.getLocation();

				// not in custom area
				if (!plugin
						.isCustomArea(plugin.get_current_area_name(guardian))) {
					plugin.getServer().broadcastMessage("自動的に海底神殿エリアが作られました");

					plugin.create_new_area(location, 50, "海底遺跡",
							"< Ocean monument >");
				}

			}
		}
	}
}
