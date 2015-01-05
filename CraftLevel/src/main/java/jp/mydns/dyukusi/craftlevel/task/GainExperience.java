package jp.mydns.dyukusi.craftlevel.task;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.requireinfo.RequirementInformation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.scheduler.BukkitRunnable;

public class GainExperience extends BukkitRunnable {
	CraftLevel plugin;
	Player player;
	boolean success;
	Material material;
	Recipe recipe;
	PlayerCraftLevelData pinf;
	RequirementInformation reqinf;

	public GainExperience(CraftLevel PLUGIN, Player PLAYER, boolean SUCCESS, Recipe RECIPE) {
		this.plugin = PLUGIN;
		this.player = PLAYER;
		this.success = SUCCESS;
		this.recipe = RECIPE;
		this.material = recipe.getResult().getType();
	}

	public void run() {
	
		this.pinf = plugin.get_player_crafting_level_info(player);
		this.reqinf = plugin.get_require_info(material);

		double failure_rate = 1.0 - plugin.get_success_rate(pinf.get_level(), material);
		int gain_exp;

		// success
		gain_exp = (int) (plugin.calc_success_exp(recipe) * failure_rate);

		// failure
		if (!success) {
			gain_exp *= plugin.get_success_rate(pinf.get_level(), material);
		}

		if (gain_exp > 0) {

			player.sendMessage(ChatColor.GREEN + "+EXP " + gain_exp + ChatColor.GOLD + " Success rate: "
					+ (plugin.get_success_rate(pinf.get_level(), material) * 100) + "%");

			// level up
			if (pinf.gain_exp(gain_exp, plugin.get_next_level_exp())) {
				plugin.getServer()
						.broadcastMessage(
								plugin.get_prefix() + " " + player.getName() + " : " + ChatColor.WHITE + "Lv "
										+ ChatColor.GOLD + (pinf.get_level() - 1) + ChatColor.WHITE + " -> "
										+ ChatColor.GOLD + pinf.get_level());

				// player.sendMessage(plugin.get_prefix() + ChatColor.WHITE +
				// "Lv " + ChatColor.GOLD
				// + (pinf.get_level() - 1) + ChatColor.WHITE + " -> " +
				// ChatColor.GOLD + pinf.get_level());
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 0.5F);
			}
		}

	}
}
