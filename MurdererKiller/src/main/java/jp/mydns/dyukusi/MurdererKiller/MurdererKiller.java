package jp.mydns.dyukusi.MurdererKiller;

import java.io.IOException;

import jp.mydns.dyukusi.MurdererKiller.command.MurdererKiller_mk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class MurdererKiller extends JavaPlugin {

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

		getServer().getPluginManager().registerEvents(new MKListener(this), this);

		this.getCommand("mk").setExecutor(new MurdererKiller_mk(this));

		String meta_key = "killed";
		for (Player p : this.getServer().getOnlinePlayers()) {
			if (p.hasMetadata(meta_key)) {
				p.removeMetadata(meta_key, this);
			}
		}

	}

	@Override
	public void onDisable() {

	}
}
