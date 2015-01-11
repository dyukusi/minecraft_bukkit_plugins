package jp.mydns.dyukusi.regenerationpoint.task;

import java.util.LinkedList;

import jp.mydns.dyukusi.regenerationpoint.RegenerationPoint;
import jp.mydns.dyukusi.regenerationpoint.regeneinfo.RegeneInfo;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Regeneration extends BukkitRunnable {

	RegenerationPoint plugin;
	LinkedList<RegeneInfo> regene_list;

	public Regeneration(RegenerationPoint regenerationPoint, LinkedList<RegeneInfo> regene) {
		this.plugin = regenerationPoint;
		this.regene_list = regene;
	}

	public void run() {
		for (RegeneInfo info : regene_list) {

			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (info.is_in_area(player)) {
					regeneration_process(player);
				}
			}
		}
	}

	private void regeneration_process(Player player) {
		// remove effects
		player.removePotionEffect(PotionEffectType.REGENERATION);

		// regene
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60 * 20, 2));

		// regene hunger level
		player.setFoodLevel(player.getFoodLevel() + 1);;

	}

}
