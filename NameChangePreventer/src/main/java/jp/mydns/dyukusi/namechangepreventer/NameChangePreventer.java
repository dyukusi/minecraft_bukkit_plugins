package jp.mydns.dyukusi.namechangepreventer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import jp.mydns.dyukusi.namechangepreventer.task.SaveUUIDName_pare;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class NameChangePreventer extends JavaPlugin implements Listener {
	HashMap<UUID, String> matching_map = new HashMap<UUID, String>();
	final String matching_path = getDataFolder().getAbsolutePath()
			+ "/uuid_name.txt";

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

		// config
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("creating config.yml file...");
			this.saveDefaultConfig();
		}

		// read save data
		Scanner sc = null;

		try {
			sc = new Scanner(new File(matching_path));
		} catch (FileNotFoundException e) {
			return;
		}

		if (sc != null) {
			while (sc.hasNextLine()) {
				String str = sc.nextLine();

				// ignore comment line
				if (str.toCharArray()[0] == '#') {
					continue;
				}

				String data[] = str.split(",");

				UUID uuid = UUID.fromString(data[1]);
				String name = data[0];

				this.matching_map.put(uuid, name);
			}

			sc.close();
		}

		//register listener
		this.getServer().getPluginManager().registerEvents(this, this);
		

	}

	@Override
	public void onDisable() {
		new SaveUUIDName_pare(this, matching_path, matching_map).run();
	}
	
	@EventHandler
	void PlayerLogin(PlayerLoginEvent event) {
		String name = event.getPlayer().getName();
		UUID uuid = event.getPlayer().getUniqueId();

		// 新規
		if (!matching_map.containsKey(uuid)) {
			
			//既に使用済みの名前
			if(matching_map.values().contains(name)){
				event.disallow(Result.KICK_OTHER,
						"Sorry, your name is already used. YourName : "
								+name);
			}

			matching_map.put(uuid, name);
		}
		// 常連
		else {

			// UUIDとプレイヤー名が初ログイン時と同じかチェック
			String map_name = matching_map.get(uuid);

			// 名前変更したプレイヤーは弾く
			if (!name.equals(map_name)) {
				getLogger().info("KICK : "+name);

				event.disallow(Result.KICK_OTHER,
						"Forbid to change player name in this server. YourName : "
								+ map_name);
			}
		}

	}

}
