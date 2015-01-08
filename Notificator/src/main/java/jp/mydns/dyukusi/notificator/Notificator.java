package jp.mydns.dyukusi.notificator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.notificator.command.PlayerNewsCommand;
import jp.mydns.dyukusi.notificator.listener.PlayerJoin;
import jp.mydns.dyukusi.notificator.playernews.News;
import jp.mydns.dyukusi.notificator.task.Notification;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mcstats.Metrics;

public class Notificator extends JavaPlugin {
	Economy economy = null;
	public String player_news_path = getDataFolder().getAbsolutePath() + "/playernews.bin";
	int server_news_interval;
	int player_news_interval;
	int player_news_char_limit;
	int player_news_charge;
	ArrayList<News> server_news;
	ArrayList<News> player_news;
	BukkitTask server_note_task;
	BukkitTask player_note_task;
	static int min = 1200;

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
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml").exists()) {
			getLogger().info("config.ymlが存在しないため、生成します。");
			this.saveDefaultConfig();
		}

		server_news_interval = this.getConfig().getInt("server_news_interval");
		player_news_interval = this.getConfig().getInt("player_news_interval");
		player_news_char_limit = this.getConfig().getInt("player_news_char_limit");
		player_news_charge = this.getConfig().getInt("player_news_charge");

		// command
		getCommand("noti").setExecutor(
				new PlayerNewsCommand(this, economy, this.player_news_char_limit, player_news_charge));

		// import server news
		this.server_news = new ArrayList<News>();
		List<String> server_msg = this.getConfig().getStringList("server_news");
		for (String msg : server_msg) {
			this.server_news.add(new News(null, msg));
		}

		// import player news
		if (new File(player_news_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(new FileInputStream(player_news_path));
				this.player_news = (ArrayList<News>) objinput.readObject();
				objinput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		// セーブデータが存在しない
		else {
			getLogger().info("playernews.bin was not found. Creating new data...");
			player_news = new ArrayList<News>();
		}

		// 1s : 20tick, 1min : 1200tick, 1h : 72000tick
		server_note_task = new Notification(this, true, true, null, server_news).runTaskTimer(this, 20, min
				* server_news_interval);
		player_note_task = new Notification(this, false, true, null, player_news).runTaskTimer(this, 20, min
				* player_news_interval);

		this.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);

	}

	@Override
	public void onDisable() {
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(new FileOutputStream(player_news_path));
			objoutput.writeObject(this.player_news);
			objoutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add_player_news(Player player, String message) {
		this.player_news.add(new News(player.getName(), message));

		player_note_task.cancel();
		player_note_task = new Notification(this, false, true, null, player_news).runTaskTimer(this, 20, min
				* player_news_interval);
	}

	boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
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
}
