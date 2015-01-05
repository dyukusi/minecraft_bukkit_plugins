package jp.mydns.dyukusi.task;

import java.util.List;

import jp.mydns.dyukusi.notificator.Notificator;
import jp.mydns.dyukusi.playernews.News;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Notification extends BukkitRunnable {
	Notificator plugin;
	boolean isServer_news;
	boolean isBroadcast;
	Player player;
	List<News> news_list;

	public Notification(Notificator notificator, boolean isServer_news, boolean isBroadcast, Player player,
			List<News> news_list) {
		this.plugin = notificator;
		this.isServer_news = isServer_news;
		this.isBroadcast = isBroadcast;
		this.player = player;
		this.news_list = news_list;
	}

	public void run() {

		if (plugin.getServer().getOnlinePlayers().size() > 0) {

			if (news_list.size() > 0) {
				String header;

				// server news
				if (isServer_news) {
					header = ChatColor.AQUA + "---- Server News ----";

					// broadcast
					if (isBroadcast) {
						plugin.getServer().broadcastMessage(header);
						for (News news : news_list) {
							plugin.getServer().broadcastMessage(news.get_message());
						}
					}
					// send to player
					else {
						player.sendMessage(header);
						for (News news : news_list) {
							player.sendMessage(news.get_message());
						}
					}

				}
				// player news
				else {
					header = ChatColor.AQUA + "---- Player News ----";

					// broadcast
					if (isBroadcast) {
						plugin.getServer().broadcastMessage(header);
						for (News news : news_list) {
							plugin.getServer().broadcastMessage(
									ChatColor.GRAY + "[" + news.get_time_str() + "]" + ChatColor.GOLD + news.get_who()
											+ ChatColor.WHITE + ": " + news.get_message());
						}
					}
					// send to player
					else {
						player.sendMessage(header);
						for (News news : news_list) {
							player.sendMessage(ChatColor.GRAY + "[" + news.get_time_str() + "]" + ChatColor.GOLD
									+ news.get_who() + ChatColor.WHITE + ": " + news.get_message());
						}
					}

				}
			}

		}
	}

}
