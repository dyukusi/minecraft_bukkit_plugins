package jp.mydns.dyukusi.craftlevel.level;

import java.io.Serializable;
import java.util.UUID;

import jp.mydns.dyukusi.craftlevel.CraftLevel;

import org.bukkit.entity.Player;

public class PlayerCraftLevelData implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private String name;
	private int level;
	private int exp;

	public PlayerCraftLevelData(UUID uuid, String player_name, int level,
			int exp) {
		this.uuid = uuid;
		this.name = player_name;
		this.level = level;
		this.exp = exp;
	}

	public UUID get_uuid() {
		return this.uuid;
	}

	public String get_player_name() {
		return this.name;
	}

	public int get_level() {
		return this.level;
	}

	public int get_exp() {
		return this.exp;

	}

	public void set_exp(int set) {
		this.exp = set;
	}

	public void set_level(int new_level) {
		this.level = new_level;
	}

	// true if level up
	public int gain_exp(int gain, int next_exp[], int max_level,
			CraftLevel plugin) {
		this.exp += gain;
		int level_up = 0;

		while (this.exp >= next_exp[level]) {
			level_up++;
			this.exp -= next_exp[level];
			this.level++;

			if (this.level >= max_level) {
				this.exp = 0;
			}

			// Tweet
			String tweet_msg = null;
			switch (this.level) {
			case 10:
				tweet_msg = name + "はCraftLevelが10になり、クラフターへの道へ一歩踏み出した！";
				break;
			case 30:
				tweet_msg = name + "はCraftLevelが30になり、鉄をも加工する術を身につけた！";
				break;
			case 60:
				tweet_msg = name + "はCraftLevel60を達成し、クラフト名人としてその名を世界に轟かせた！";
				break;
			default:
				break;
			}

			if (tweet_msg != null) {
				plugin.getServer().dispatchCommand(
						plugin.getServer().getConsoleSender(),
						"ta tweet " + tweet_msg);
			}

		}
		
		return level_up;
	}
}
