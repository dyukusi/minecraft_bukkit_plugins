package jp.mydns.dyukusi.effectarea.listener;

import jp.mydns.dyukusi.effectarea.EffectArea;
import jp.mydns.dyukusi.effectarea.regeneinfo.EffectAreaInfo;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MonsterSpawn implements Listener {

	EffectArea plugin;

	public MonsterSpawn(EffectArea effectArea) {
		this.plugin = effectArea;
	}
	
	@EventHandler
	void DeathEvent(PlayerDeathEvent event){
	
		
	}

	@EventHandler
	void CreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof Monster || event.getEntity() instanceof Animals) {

			EffectAreaInfo ea = plugin.is_in_effect_area(event.getEntity()
					.getLocation());

			// in effect area
			if (ea != null) {

				// disabled monster spawn
				if (!ea.get_monsterspawn()) {
					event.setCancelled(true);
				}

			}

		}
	}
}
