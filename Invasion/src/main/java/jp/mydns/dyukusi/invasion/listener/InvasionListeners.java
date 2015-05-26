package jp.mydns.dyukusi.invasion.listener;

import jp.mydns.dyukusi.invasion.Invasion;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InvasionListeners implements Listener {

	Invasion plugin;

	public InvasionListeners(Invasion invasion) {
		this.plugin = invasion;
	}

	@EventHandler
	void SpawnCustomMob(CreatureSpawnEvent event) {

		LivingEntity entity = event.getEntity();

		if (event.getSpawnReason().equals(SpawnReason.CUSTOM)) {

			entity.setRemoveWhenFarAway(false);

			int jump_lv = entity.getMetadata("jump_lv").get(0).asInt();

			if (jump_lv > 0) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
						Integer.MAX_VALUE, jump_lv));
			}

		}

	}

}
