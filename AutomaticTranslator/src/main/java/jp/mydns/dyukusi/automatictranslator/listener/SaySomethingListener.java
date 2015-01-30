package jp.mydns.dyukusi.automatictranslator.listener;

import java.util.HashMap;
import java.util.Map;

import jp.mydns.dyukusi.automatictranslator.AutomaticTranslator;
import jp.mydns.dyukusi.automatictranslator.task.ChatTask;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.memetix.mst.language.Language;

public class SaySomethingListener implements Listener {

	AutomaticTranslator plugin;
	Map<Language, Language> lang;

	public SaySomethingListener(AutomaticTranslator autotranslate) {
		this.plugin = autotranslate;

		lang = new HashMap<Language, Language>();

		// default translation language setting
		for (Language lan_list : Language.values()) {
			if (lan_list.equals(Language.JAPANESE)) {
				lang.put(lan_list, Language.ENGLISH);
			} else {
				lang.put(lan_list, Language.JAPANESE);
			}
		}

	}

	@EventHandler
	void SaySomething(AsyncPlayerChatEvent event) throws Exception {
		event.setCancelled(true);

		String msg = event.getMessage();
		Player player = event.getPlayer();

		// new ChatTask(plugin, lang, player, msg).run();
		player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1F, 1.2F);

		new jp.mydns.dyukusi.automatictranslator.task.TranslateTask(plugin,
				lang, player, msg).runTaskAsynchronously(plugin);

		// plugin.getServer().broadcastMessage("say something");

	}

}
