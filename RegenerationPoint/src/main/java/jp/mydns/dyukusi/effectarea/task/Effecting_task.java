package jp.mydns.dyukusi.effectarea.task;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jp.mydns.dyukusi.effectarea.EffectArea;
import jp.mydns.dyukusi.effectarea.regeneinfo.EffectAreaInfo;
import jp.mydns.dyukusi.effectarea.regeneinfo.EffectInfo;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class Effecting_task extends BukkitRunnable {

	EffectArea plugin;
	LinkedHashMap<String, EffectAreaInfo> effect_area_map;

	public Effecting_task(EffectArea regenerationPoint,
			LinkedHashMap<String, EffectAreaInfo> effect_area_map) {
		this.plugin = regenerationPoint;
		this.effect_area_map = effect_area_map;
	}

	public synchronized void run() {

		if (effect_area_map != null) {

			for (Entry<String, EffectAreaInfo> ent : effect_area_map.entrySet()) {
				EffectAreaInfo info = ent.getValue();

				for (Player player : plugin.getServer().getOnlinePlayers()) {
					if (info.is_in_area(player.getLocation())) {
						effect_process(player, info);
						player.setMetadata("effect_area",
								new FixedMetadataValue(plugin, true));
					}
					// not in effect area
					else {

						// was in custom area
						if (player.getMetadata("effect_area").size() > 0
								&& player.getMetadata("effect_area").get(0)
										.asBoolean()) {
							player.setMetadata("effect_area",
									new FixedMetadataValue(plugin, false));

							player.setWalkSpeed(0.2F);
						}

					}
				}

			}
		}
		//null error
		else{
			this.cancel();
		}
		
	}

	private void effect_process(Player player, EffectAreaInfo area_info) {

		for (EffectInfo effect_info : area_info.get_effect_list()) {
			PotionEffect effect = effect_info.get_PotionEffect();

			// if (!player.hasPotionEffect(effect.getType()))
			player.removePotionEffect(effect.getType());
			player.addPotionEffect(effect);

		}

		player.setWalkSpeed(area_info.get_walking_speed());

		// regene hunger level
		int speed;
		if ((speed = area_info.get_hunger_regene_speed()) > 0) {
			player.setFoodLevel(player.getFoodLevel() + speed);

			if (player.getFoodLevel() > 20) {
				player.setFoodLevel(20);
			}

		}

	}

}
