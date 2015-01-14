package jp.mydns.dyukusi.weathereffect;

import java.io.IOException;

import jp.mydns.dyukusi.weathereffect.listener.RainBreakBlock;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class WeatherEffect extends JavaPlugin {

	// 起動処理
	public void onEnable() {
		getLogger().info("WetherEffect Enabled Sucsessfully!!!!!!!!!!!!");
		// mcstats
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.RED + "Failed to submit the stats");
			// Failed to submit the stats
		}

		this.getServer().getPluginManager()
				.registerEvents(new RainBreakBlock(this), this);
	}

	// 終了処理
	public void onDisable() {
		getLogger().info("WetherEffect Disabled Sucsessfully!!!!!!!!!!!!");
	}

}
