package jp.mydns.dyukusi.seasonalfood.task;

import java.util.Calendar;

import jp.mydns.dyukusi.seasonalfood.SeasonalFood;
import jp.mydns.dyukusi.seasonalfood.seasontype.SeasonType;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SeasonChanger extends BukkitRunnable {

	SeasonalFood plugin;
	int current_date;

	public SeasonChanger(SeasonalFood seasonalFood) {
		this.plugin = seasonalFood;
		current_date = Calendar.getInstance().get(Calendar.DATE);
	}

	public void run() {

		int new_date;

		if (!plugin.get_force_next_season()) {
			new_date = Calendar.getInstance().get(Calendar.DATE);
		}
		// force next season
		else {
			new_date = -1;
		}

		if (current_date != new_date) {
			current_date = new_date;
			SeasonType next_season = SeasonType.values()[(plugin.get_current_season().get_index() + 1)
					% SeasonType.values().length];
			plugin.set_current_season(next_season);
			plugin.display_season(true, null, next_season);

			for (Player player : plugin.getServer().getOnlinePlayers()) {
				player.sendMessage(plugin.get_prefix()+" "+next_season.get_inJapanese()+"になりました。〜が旬ですやらなんやら〜");
			}
		}

		// forced next season
		if (plugin.get_force_next_season()) {
			current_date = Calendar.getInstance().get(Calendar.DATE);
			plugin.set_force_next_season(false);
		}

		// plugin.getServer().broadcastMessage(c.get(Calendar.SECOND)+" 秒");

	}

}
