package jp.mydns.dyukusi.fishinglevel.playerdata;

import jp.mydns.dyukusi.fishinglevel.FishingLevel;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerFishingLevelData {
	private String player_name;
	private int level;
	private int exp;

	public PlayerFishingLevelData(String name, int level, int exp) {
		this.player_name = name;
		this.level = level;
		this.exp = exp;
	}

	public String get_player_name() {
		return this.player_name;
	}

	public int get_level() {
		return this.level;
	}

	public int get_exp() {
		return this.exp;
	}

	public void add_exp(int amount) {
		this.exp += amount;
	}

	public void set_level(int level) {
		this.level = level;
	}

	public void set_exp(int amount) {
		this.exp = amount;
	}

	public void level_up_or_not(FishingLevel plugin, Player player,
			int required_exp) {

		// level up
		if (this.exp >= required_exp) {
			plugin.getServer().broadcastMessage(
					ChatColor.BLUE + "[FishingLevel] " + ChatColor.WHITE
							+ player.getName() + ": " + ChatColor.AQUA
							+ this.get_level() + " -> "
							+ (this.get_level() + 1));
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 0.5F);

			this.set_level(this.get_level() + 1);
			this.set_exp(this.exp - required_exp);
			this.level_up_or_not(plugin, player,
					plugin.get_required_exp_from_level(this.get_level()));
		}

	}

}
