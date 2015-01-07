package jp.mydns.dyukusi.craftlevel.level;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerCraftLevelData implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private String name;
	private int level;
	private int exp;

	public PlayerCraftLevelData(Player p) {
		this.uuid = p.getUniqueId();
		this.name = p.getName();
		this.level = 1;
		this.exp = 0;
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
	public boolean gain_exp(int gain, int next_exp[], int max_level) {
		this.exp += gain;

		if (this.exp >= next_exp[level]) {
			this.exp -= next_exp[level];
			this.level++;

			if (this.level >= max_level) {
				this.exp = 0;
			}

			return true;
		}

		return false;
	}

}
