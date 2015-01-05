package jp.mydns.dyukusi.devilnetherportal;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class DevilNetherPortal extends JavaPlugin {
	private String prefix = ChatColor.LIGHT_PURPLE + "[" + this.getName() + "]"+ChatColor.WHITE;

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

		this.getServer().getPluginManager().registerEvents(new listener(this), this);
		getCommand("escape").setExecutor(new command(this));
	}

	@Override
	public void onDisable() {

	}

	public String get_prefix() {
		return this.prefix;
	}

}
