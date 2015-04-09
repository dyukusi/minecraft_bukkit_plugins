package jp.mydns.dyukusi.seasonalfood.task;

import java.util.Calendar;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
import jp.mydns.dyukusi.seasonalfood.seasontype.SeasonType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.internal.builders.AnnotatedBuilder;

public class SeasonChanger extends BukkitRunnable {

	SeasonalFood plugin;
	SeasonType current_season;

	public SeasonChanger(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
		current_season = get_current_season();
	}

	SeasonType get_current_season() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

		return SeasonType.values()[(day / 3) % SeasonType.values().length];
	}

	public void run() {

		SeasonType new_season;

		if (!plugin.get_force_next_season()) {
			new_season = get_current_season();
		}
		// force next season
		else {
			new_season = get_current_season();
			// new_date = -1;
		}

		if (current_season != new_season) {
			current_season = new_season;
			// SeasonType next_season = SeasonType.values()[(plugin
			// .get_current_season().get_index() + 1)
			// % SeasonType.values().length];

			plugin.set_current_season(new_season);
			plugin.display_season(true, null, new_season);

			String seasonal_crop;
			String seasonal_crop_eng;

			switch (new_season) {
			case SPRING:
				seasonal_crop = "小麦とカカオ";
				seasonal_crop_eng = "Wheat and Cocoa";
				break;
			case SUMMER:
				seasonal_crop = "じゃがいもとスイカ";
				seasonal_crop_eng = "Potatoe and Watermelon";
				break;
			case AUTUMN:
				seasonal_crop = "ニンジンとカボチャ";
				seasonal_crop_eng = "Carrot and Pumpkin";
				break;
			case WINTER:
				seasonal_crop = "";
				seasonal_crop_eng = "";
				break;

			default:
				seasonal_crop = "ERROR";
				seasonal_crop_eng = "ERROR";
				break;
			}

			String season_change_str = ChatColor.RED + "ERROR";

			// spring summer autumn
			if (!new_season.equals(SeasonType.WINTER)) {

				for (Player player : plugin.getServer().getOnlinePlayers()) {
					player.sendMessage(plugin.get_prefix() + " "
							+ new_season.get_inJapanese() + "になりました。"
							+ seasonal_crop + "が旬になりました。");
					player.sendMessage(ChatColor.AQUA + "< " + new_season
							+ " has come. " + seasonal_crop_eng + " are now in season! >");

				}

			} else {

				for (Player player : plugin.getServer().getOnlinePlayers()) {
					player.sendMessage(plugin.get_prefix() + " "
							+ new_season.get_inJapanese()
							+ "になりました。 蓄えた食料で冬を乗り越えましょう。");
					player.sendMessage(ChatColor.AQUA + "< " + new_season
							+ " has come. Survive the harsh winter with your food reserves. >");
				}
			}

		}

		// forced next season
		if (plugin.get_force_next_season()) {
			// te = Calendar.getInstance().get(Calendar.DATE);
			plugin.set_force_next_season(false);
		}

		// plugin.getServer().broadcastMessage(c.get(Calendar.SECOND)+" 秒");

	}

}
