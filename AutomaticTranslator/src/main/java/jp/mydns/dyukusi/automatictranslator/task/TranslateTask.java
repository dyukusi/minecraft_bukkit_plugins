package jp.mydns.dyukusi.automatictranslator.task;

import java.util.Map;

import jp.mydns.dyukusi.automatictranslator.AutomaticTranslator;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class TranslateTask extends BukkitRunnable {

	AutomaticTranslator plugin;
	Map<Language, Language> lang;
	Player player;
	String msg;

	public TranslateTask(AutomaticTranslator plugin,
			Map<Language, Language> lang, Player player, String msg) {
		this.plugin = plugin;
		this.lang = lang;
		this.player = player;
		this.msg = msg;
	}

	@Override
	public void run() {
		Language to;

		if (player.hasMetadata("translate")) {

			// off
			if ((player.getMetadata("translate").get(0).value()).equals("off")) {

			}
			// on
			else {
				to = (Language) player.getMetadata("translate").get(0).value();
				try {
					msg = msg + ChatColor.AQUA + "\n<"
							+ Translate.execute(msg, to) + ">";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// default
		else {
			try {
				to = lang.get(Detect.execute(msg));

				msg = msg + ChatColor.AQUA + " <" + Translate.execute(msg, to)
						+ ">";

			} catch (Exception e) {
				plugin.getLogger().info(ChatColor.RED + e.getMessage());
				// ignore error
			}

		}
		plugin.getServer().broadcastMessage(
				"<" + ChatColor.BOLD + player.getName() + ChatColor.WHITE + "> "
						+ msg);
		
		for(Player onp : plugin.getServer().getOnlinePlayers()){
			onp.playSound(onp.getLocation(), Sound.CAT_MEOW, 1.0F, 1.0F);
		}
		
		// chat
		// player.chat("test message");

		// return;
		// plugin.getServer().getScheduler().cancelTask(this.getTaskId());
	}

}
