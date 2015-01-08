package jp.mydns.dyukusi.seasonalfood.seasontype;

import org.bukkit.ChatColor;

public enum SeasonType {
	SPRING(0), SUMMER(1), AUTUMN(2), WINTER(3);

	private final int index;

	private SeasonType(int idx) {
		this.index = idx;
	}

	public SeasonType int_to_season(int index) {
		switch (index) {
		case 0:
			return SPRING;
		case 1:
			return SUMMER;
		case 2:
			return AUTUMN;
		case 3:
			return WINTER;
		default:
			return SPRING;
		}
	}

	public String get_inJapanese() {
		String japanese[] = { "春", "夏", "秋", "冬" };
		return japanese[this.index];
	}

	public ChatColor get_season_color() {
		ChatColor[] color = { ChatColor.YELLOW, ChatColor.RED, ChatColor.GOLD, ChatColor.WHITE };
		return color[this.index];
	}

	public int get_index() {
		return this.index;
	}

}
