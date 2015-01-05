package jp.mydns.dyukusi.myplugin.task;

import jp.mydns.dyukusi.myplugin.MyPlugin;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FireProtection extends BukkitRunnable {

	MyPlugin plugin;
	Player player;

	public FireProtection(MyPlugin myplugin, Player player) {
		this.plugin = myplugin;
		this.player = player;
	}

	public void run() {
		// plugin.getServer().broadcastMessage(ChatColor.YELLOW + "CHECK : " +
		// player.getActivePotionEffects());

		// fire protecting
		if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
			for (PotionEffect effect : player.getActivePotionEffects()) {
				if (effect.getType().equals(PotionEffectType.FIRE_RESISTANCE)) {

					// remove effects
					player.removePotionEffect(PotionEffectType.BLINDNESS);
					player.removePotionEffect(PotionEffectType.HUNGER);
					player.removePotionEffect(PotionEffectType.WEAKNESS);

					// blind
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, effect.getDuration(), 1));

					// hunger
					player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, effect.getDuration(), 1));

					// weakness
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, effect.getDuration(), 1));

					break;
				}
			}
		}
	}
}
