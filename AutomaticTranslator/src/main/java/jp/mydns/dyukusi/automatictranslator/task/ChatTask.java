package jp.mydns.dyukusi.automatictranslator.task;

import java.util.Map;

import jp.mydns.dyukusi.automatictranslator.AutomaticTranslator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class ChatTask extends BukkitRunnable {

	AutomaticTranslator plugin;
	Map<Language, Language> lang;
	Player player;
	String msg;

	public ChatTask(AutomaticTranslator plugin, Map<Language, Language> lang,
			Player player, String msg) {
		this.plugin = plugin;
		this.lang = lang;
		this.player = player;
		this.msg = msg;
	}

	@Override
	public void run() {
		plugin.getServer().broadcastMessage("ChatTask");

		new TranslateTask(plugin, lang, player, msg)
				.runTaskAsynchronously(plugin);
	}

}
