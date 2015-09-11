package jp.mydns.dyukusi.craftlevel.task;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.config.Message;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.materialinfo.MaterialInfo;

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
	MaterialInfo material_info;
	Recipe recipe;
	PlayerCraftLevelData pinfo;
	Material result;
	int success_rate;

	public GainExperience(CraftLevel PLUGIN, Player PLAYER, boolean SUCCESS,
			Recipe RECIPE, Material RESULT) {
		this.plugin = PLUGIN;
		this.player = PLAYER;
		this.success = SUCCESS;
		this.recipe = RECIPE;
		this.material_info = CraftLevel.get_material_info(RESULT);
		this.pinfo = plugin.get_player_crafting_level_info(player);
		this.success_rate = (int) (material_info.get_success_rate(pinfo
				.get_level()) * 100);
	}

	public void run() {

		// lower than max level
		if (pinfo.get_level() < CraftLevel.get_max_craft_level()) {

			double failure_rate = 1.0 - material_info.get_success_rate(pinfo
					.get_level());
			int gain_exp;

			// success
			gain_exp = (int) (material_info.get_success_exp(recipe) * failure_rate);

			// failure
			if (!success) {
				gain_exp *= material_info.get_success_rate(pinfo.get_level());
			}

			if (material_info.get_custom_experience() >= 0) {
				gain_exp = material_info.get_custom_experience();
			}

			if (gain_exp > 0) {

				int levelup = pinfo.gain_exp(gain_exp,
						CraftLevel.get_next_level_exp(),
						CraftLevel.get_max_craft_level(), plugin);

				player.sendMessage(Message.Craft_Gain_Experience.get_message(
						gain_exp, pinfo.get_exp(),
						CraftLevel.get_next_level_exp()[pinfo.get_level()],
						success_rate));

				// level up
				if (levelup > 0) {

					// broadcast?
					if (plugin.get_broadcast_levelup()) {
						plugin.getServer().broadcastMessage(
								Message.Craft_Levelup.get_message(
										player.getName(), pinfo.get_level()
												- levelup, pinfo.get_level()));
					}
					// send message to the player only
					else {
						player.sendMessage(Message.Craft_Levelup.get_message(
								player.getName(), pinfo.get_level() - levelup,
								pinfo.get_level()));
					}

					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,
							0.5F);
				}

			}
		} else {
			player.sendMessage(Message.Craft_Success_Rate.get_message(
					success_rate, true));
		}

	}
}
