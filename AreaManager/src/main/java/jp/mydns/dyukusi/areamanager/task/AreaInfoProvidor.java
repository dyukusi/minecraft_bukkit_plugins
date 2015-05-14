package jp.mydns.dyukusi.areamanager.task;

import jp.mydns.dyukusi.areamanager.AreaManager;
import jp.mydns.dyukusi.areamanager.areainfo.AreaInformation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class AreaInfoProvidor extends BukkitRunnable {

	AreaManager plugin;
	Player player;

	public AreaInfoProvidor(AreaManager areaManager, Player player) {
		this.plugin = areaManager;
		this.player = player;
	}

	public void run() {

		// player left the game
		if (player == null || !player.isOnline()) {
			this.cancel();
		}

		// change player current area
		if (!plugin.isSame_area_with_last_time(player)) {
			String current_area_name = plugin.get_current_area_name(player);

			AreaInformation info = plugin.get_area_info(current_area_name);

			player.removeMetadata("am_area", plugin);
			player.setMetadata("am_area", new FixedMetadataValue(plugin,
					current_area_name));

			if (info != null) {

				int sec = (int) (System.currentTimeMillis() / 1000);
				int min = sec / 60;
				int hour = min / 60;

				int diff_hour = hour - info.get_last_played_time();

				boolean time_up = (!plugin
						.getServer()
						.getOnlinePlayers()
						.contains(
								plugin.getServer()
										.getOfflinePlayer(info.get_owner_name())
										.getPlayer()) && diff_hour >= 24 * 10) ? true
						: false;
				
				//initialize land price
				if (time_up || info.get_owner_name().equals("none")) {
					info.set_price(info.get_initial_price());
				}			

				//can buy or not
				if(info.get_owner_want_to_sell() || time_up || info.get_owner_name().equals("none")){
					info.set_can_buy(true);
				}
				else{
					info.set_can_buy(false);
				}
				
				if (!player.hasMetadata("hide_own_area_info")
						|| !info.get_owner_name().equals(player.getName())) {

					String AreaName;

					if (!info.get_custom_area_name().equals("null")) {
						AreaName = info.get_custom_area_name();
					} else {
						AreaName = info.get_area_name();
					}

					player.sendMessage(ChatColor.DARK_RED + "--------"
							+ ChatColor.GOLD + AreaName + ChatColor.DARK_RED
							+ "--------");
					player.sendMessage(ChatColor.YELLOW + "地主" + ChatColor.AQUA
							+ "<LandOwner>" + ChatColor.WHITE + " : "
							+ ChatColor.GOLD + info.get_owner_name());
					player.sendMessage(ChatColor.YELLOW + "地価" + ChatColor.AQUA
							+ "<LandPrice>" + ChatColor.WHITE + " : "
							+ ChatColor.GOLD + info.get_price());

					String canbuy;

					if (info.get_can_buy()) {
						canbuy = ChatColor.GREEN + "売出中 " + ChatColor.AQUA
								+ "<Now on sale>";
					} else {
						canbuy = ChatColor.RED + "占有中 " + ChatColor.AQUA
								+ "<Not available now>";
					}

					player.sendMessage(ChatColor.YELLOW + "状態" + ChatColor.AQUA
							+ "<Status>" + ChatColor.WHITE + " : " + canbuy);

					if (info.get_can_buy()) {
						player.sendMessage(ChatColor.LIGHT_PURPLE + "/am buy "
								+ ChatColor.GREEN + "で土地を購入することができます。");
						player.sendMessage(ChatColor.AQUA
								+ " < Execute [ /am buy ] to buy this land. >");
					}

				}
			}
		}

	}
}
