package jp.mydns.dyukusi.playernews;

import java.io.Serializable;
import java.util.Calendar;

import org.bukkit.entity.Player;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	private String who;
	private Integer[] time;
	private String message;

	public News(String player_name, String msg) {
		this.who = player_name;

		Calendar cl = Calendar.getInstance();
		this.time = new Integer[] { cl.get(Calendar.YEAR), cl.get(Calendar.MONTH)+1, cl.get(Calendar.DATE),
				cl.get(Calendar.AM_PM), cl.get(Calendar.HOUR), cl.get(Calendar.MINUTE) };

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
}
