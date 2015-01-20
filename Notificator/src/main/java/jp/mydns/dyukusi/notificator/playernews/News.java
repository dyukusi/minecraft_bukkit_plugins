package jp.mydns.dyukusi.notificator.playernews;

import java.io.Serializable;
import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	private String who;
	private Integer[] time;
	private String message;

	public News(Integer[] time, String player_name, String msg) {
		this.who = player_name;
		this.time = time;
		this.message = msg;
	}

	public String get_who() {
		return this.who;
	}

	public Integer[] get_time() {
		return this.time;
	}

	public String get_message() {
		return this.message;
	}

	public String get_time_str() {
		return time[0] + "-" + time[1] + "-" + time[2];
	}

	@Override
	public String toString() {
		return ChatColor.GRAY + "[" + this.get_time_str() + "]"
				+ ChatColor.GOLD + this.get_who() + ChatColor.WHITE + ": "
				+ this.get_message();
	}
}
