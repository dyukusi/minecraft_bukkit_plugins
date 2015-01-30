package jp.mydns.dyukusi.automatictranslator;

import java.io.IOException;

import jp.mydns.dyukusi.automatictranslator.listener.SaySomethingListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.memetix.mst.MicrosoftTranslatorAPI;

public class AutomaticTranslator extends JavaPlugin {
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

		// Microsoft Translate API
		MicrosoftTranslatorAPI.setClientId("DyukusiServer");
		MicrosoftTranslatorAPI.setClientSecret("g/OFmFEQVDvX3X8vl9yTeHzVvL9mY2t+H1A+SQR0JIE=");

		// remove language meta data
		String meta_key = "translate";
		for (Player p : this.getServer().getOnlinePlayers()) {
			if (p.hasMetadata(meta_key)) {
				p.removeMetadata(meta_key, this);
			}
		}
		
		//listener register
		this.getServer().getPluginManager().registerEvents(new SaySomethingListener(this), this);

	}

	@Override
	public void onDisable() {

	}
}
