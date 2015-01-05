package jp.mydns.dyukusi;

import java.util.HashMap;
import java.util.Map;

import jp.mydns.dyukusi.automatictranslator.AutomaticTranslator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class Translation implements Listener {

	AutomaticTranslator plugin;
	Map<Language, Language> lang;

	public Translation(AutomaticTranslator autotranslate) {
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
		String msg = event.getMessage();
		Language to;

		if (event.getPlayer().hasMetadata("translate")) {

			// off
			if ((event.getPlayer().getMetadata("translate").get(0).value()).equals("off")) {

			}
			// on
			else {
				to = (Language) event.getPlayer().getMetadata("translate").get(0).value();
				event.setMessage(msg + ChatColor.AQUA + "\n<" + Translate.execute(event.getMessage(), to) + ">");
			}
		}
		// default
		else {
			to = lang.get(Detect.execute(msg));
			// event.setMessage(msg + ChatColor.AQUA + "\n<" +
			// Translate.execute(event.getMessage(), to) + ">");

			try {

				event.setFormat("<" + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.WHITE + "> " + msg
						+ ChatColor.AQUA + " <" + Translate.execute(event.getMessage(), to) + ">");

			} catch (Exception e) {
				plugin.getLogger().info(ChatColor.RED + e.getMessage());
				// ignore error
			}

		}
	}

}
