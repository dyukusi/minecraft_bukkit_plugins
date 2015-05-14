package jp.mydns.dyukusi.seasonalfood.task;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
import jp.mydns.dyukusi.seasonalfood.seasontype.SeasonType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SeasonChanger extends BukkitRunnable {

	SeasonalFood plugin;
	SeasonType current_season;

	public SeasonChanger(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
		current_season = seasonalFood.get_current_season();
	}

	public void run() {

		SeasonType new_season;

		if (!plugin.get_force_next_season()) {
			new_season = plugin.get_current_season();
			// new_season = this.current_season;
		}
		// force next season
		else {
			new_season = SeasonType.values()[(current_season.get_index() + 1)
					% SeasonType.values().length];
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

			String season_change_jp = ChatColor.RED + "ERROR";
			String season_change_en = ChatColor.RED + "ERROR";

			// spring summer autumn
			if (!new_season.equals(SeasonType.WINTER)) {
				season_change_jp = plugin.get_prefix() + " "
						+ new_season.get_inJapanese() + "になりました。"
						+ seasonal_crop + "が旬になりました。";
				season_change_en = ChatColor.AQUA + "< " + new_season
						+ " has come. " + seasonal_crop_eng
						+ " are now in season! >";
			} else {

				season_change_jp = plugin.get_prefix() + " "
						+ new_season.get_inJapanese()
						+ "になりました。 蓄えた食料で冬を乗り越えましょう。";
				season_change_en = ChatColor.AQUA
						+ "< "
						+ new_season
						+ " has come. Survive the harsh winter with your food reserves. >";
			}

			for (Player player : plugin.getServer().getOnlinePlayers()) {
				player.sendMessage(season_change_jp);
				player.sendMessage(season_change_en);
			}

			// Tweet
			plugin.getServer().dispatchCommand(
					plugin.getServer().getConsoleSender(),
					"ta tweet " + season_change_jp);

		}

		// forced next season
		if (plugin.get_force_next_season()) {
			// te = Calendar.getInstance().get(Calendar.DATE);
			plugin.set_force_next_season(false);
		}

		// plugin.getServer().broadcastMessage(c.get(Calendar.SECOND)+" 秒");

	}

}
