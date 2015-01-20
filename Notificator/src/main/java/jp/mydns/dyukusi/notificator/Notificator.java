package jp.mydns.dyukusi.notificator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import jp.mydns.dyukusi.notificator.command.PlayerNewsCommand;
import jp.mydns.dyukusi.notificator.listener.PlayerJoin;
import jp.mydns.dyukusi.notificator.playernews.News;
import jp.mydns.dyukusi.notificator.task.Notification;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mcstats.Metrics;

public class Notificator extends JavaPlugin {
	Economy economy = null;
	public String player_news_path = getDataFolder().getAbsolutePath()
			+ "/playernews.txt";
	private int server_news_interval;
	private int player_news_display_limit;
	private int player_news_interval;
	private int player_news_char_limit;
	private int player_news_charge;
	private ArrayList<News> server_news;
	private ArrayList<News> player_news;
	private BukkitTask server_note_task;
	private BukkitTask player_note_task;
	static final int min = 1200;

	@Override
	public void onEnable() {
		
		// mcstats
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.RED + "Failed to submit the stats");
			// Failed to submit the stats
		}

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// config
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.ymlが存在しないため、生成します。");
			this.saveDefaultConfig();
		}

		server_news_interval = this.getConfig().getInt("server_news_interval");
		player_news_display_limit = this.getConfig().getInt(
				"player_news_display_limit");
		player_news_interval = this.getConfig().getInt("player_news_interval");
		player_news_char_limit = this.getConfig().getInt(
				"player_news_char_limit");
		player_news_charge = this.getConfig().getInt("player_news_charge");

		// command
		getCommand("noti").setExecutor(
				new PlayerNewsCommand(this, economy,
						this.player_news_char_limit, player_news_charge));

		// import server news
		this.server_news = new ArrayList<News>();
		List<String> server_msg = this.getConfig().getStringList("server_news");
		for (String msg : server_msg) {
			this.server_news.add(new News(this.get_current_time(), null, msg));
		}

		// import player news
		if (new File(player_news_path).exists()) {
			this.player_news = new ArrayList<News>();

			Scanner sc = null;
			try {
				sc = new Scanner(new File(player_news_path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (sc != null) {
				while (sc.hasNextLine()) {
					String str = sc.nextLine();

					// year,month,day,am_pm,hour,minute,name,message
					String[] data = str.split(",");

					int am_pm;
					if (data[3].equals("AM")) {
						am_pm = Calendar.AM;
					} else {
						am_pm = Calendar.PM;
					}

					Integer[] time = { Integer.parseInt(data[0]),
							Integer.parseInt(data[1]),
							Integer.parseInt(data[2]), am_pm,
							Integer.parseInt(data[4]),
							Integer.parseInt(data[5]) };

					String player = data[6];
					String message = data[7];

					this.add_player_news(time, player, message, false);
				}

				// reverse array list
				Collections.reverse(this.player_news);

			}

		}
		// セーブデータが存在しない
		else {
			getLogger().info(
					"playernews.bin was not found. Creating new data...");
			player_news = new ArrayList<News>();
		}

		// 1s : 20tick, 1min : 1200tick, 1h : 72000tick
		server_note_task = new Notification(this, true, true, null,
				server_news, player_news_display_limit).runTaskTimer(this, 20,
				min * server_news_interval);
		player_note_task = new Notification(this, false, true, null,
				player_news, player_news_display_limit).runTaskTimer(this, 20,
				min * player_news_interval);

		this.getServer().getPluginManager()
				.registerEvents(new PlayerJoin(this), this);

	}

	@Override
	public void onDisable() {
		File file = new File(player_news_path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (pw != null) {

			for (News news : this.player_news) {
				Integer[] time = news.get_time();
				String am_pm;
				if (time[3].equals(Calendar.AM)) {
					am_pm = "AM";
				} else {
					am_pm = "PM";
				}

				String save_message = time[0] + "," + time[1] + "," + time[2]
						+ "," + am_pm + "," + time[4] + "," + time[5] + ","
						+ news.get_who() + "," + news.get_message();

				pw.println(save_message);
			}

			pw.close();
			getLogger().info("Player message save completed!!");

		} else {
			getLogger().info(ChatColor.RED + "Couldn't save player message.");
		}

	}

	public Integer[] get_current_time() {
		Calendar cl = Calendar.getInstance();
		Integer[] time = new Integer[] { cl.get(Calendar.YEAR),
				cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DATE),
				cl.get(Calendar.AM_PM), cl.get(Calendar.HOUR),
				cl.get(Calendar.MINUTE) };

		return time;
	}

	public void add_player_news(Integer[] time, String player_name,
			String message, boolean notify_broadcast) {
		this.player_news.add(0, new News(time, player_name, message));

		if (notify_broadcast) {
			player_note_task.cancel();
			player_note_task = new Notification(this, false, true, null,
					player_news, player_news_display_limit).runTaskTimer(this,
					20, min * player_news_interval);
		}
	}

	boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			this.economy = economyProvider.getProvider();
		}
		return (economy != null);
	}

	public ArrayList<News> get_server_news() {
		return this.server_news;
	}

	public ArrayList<News> get_player_news() {
		return this.player_news;
	}

	public int get_player_news_display_limit() {
		return this.player_news_display_limit;
	}

	public void display_help(CommandSender sender) {
		String splitter = ChatColor.LIGHT_PURPLE
				+ "-----------Notificator-----------";
		String help1 = ChatColor.GOLD + "/noti pnews [page number] "
				+ ChatColor.WHITE + ": " + ChatColor.GREEN
				+ "Display player news.";
		String help2 = ChatColor.GOLD + "/noti pnews add [Message] "
				+ ChatColor.WHITE + ": " + ChatColor.GREEN
				+ "Add new player message and pay charge.";
		String help3 = ChatColor.GOLD
				+ "/noti pnews delete [index number] "
				+ ChatColor.WHITE
				+ ": "
				+ ChatColor.GREEN
				+ "Delete player message. Check index by executing \"/noti pnews [page number]\" command.";
		String end = ChatColor.LIGHT_PURPLE
				+ "-------------------------------";

		LinkedList<String> help = new LinkedList<String>();
		help.add(splitter);
		help.add(help1);
		help.add(help2);
		help.add(help3);
		help.add(end);

		for (String msg : help) {
			sender.sendMessage(msg);
		}
	}
}
