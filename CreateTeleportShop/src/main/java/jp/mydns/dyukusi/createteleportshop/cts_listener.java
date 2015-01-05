package jp.mydns.dyukusi.createteleportshop;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mccraftaholics.warpportals.api.WarpPortalsCreateEvent;
import com.mccraftaholics.warpportals.api.WarpPortalsEvent;
import com.mccraftaholics.warpportals.api.WarpPortalsTeleportEvent;

public class cts_listener implements Listener {
	CreateTeleportShop plugin;
	Economy economy;

	public cts_listener(CreateTeleportShop cts, Economy eco) {
		this.plugin = cts;
		this.economy = eco;
	}

	@EventHandler
	public void CreateCustomPortalEvent(WarpPortalsCreateEvent event) {
		Player creater = (Player) event.getSender();

		plugin.SaveCreatePortalData(event.getPortal().name,
				new Portal_Information(creater.getUniqueId(), 0, "NO COMMENT"));
	}

	@EventHandler
	public void PlayerPortalEvent(WarpPortalsEvent event) {
		Player player = event.getPlayer();
		Portal_Information portal_info = plugin.get_creater_map().get(
				event.getPortal().name);
		Player CREATER = plugin.getServer().getPlayer(
				portal_info.get_creater_uuid());
		int FREQUENCY = portal_info.get_frequency();
		int FARE = 10;
		int CHARGE = portal_info.get_charge();
		String COMMENT = portal_info.get_comment();
		int TOTAL_FEE = FARE + CHARGE;

		// 素手の場合
		if (player.getItemInHand().getType().equals(Material.AIR)) {
			double money = economy.getBalance(player);
			// 料金は足りているか？
			if (money >= TOTAL_FEE) {
				economy.withdrawPlayer(player, TOTAL_FEE);
				player.sendMessage(ChatColor.WHITE + "" + TOTAL_FEE
						+ ChatColor.GREEN + " ＄を支払ってテレポートした！ " + ChatColor.AQUA
						+ "< You have teleported for " + ChatColor.WHITE
						+ TOTAL_FEE + " ＄! >");

				//送金処理
				player.performCommand("od deposit " + CREATER.getName() + " "
						+ CHARGE + " " + "ポータル手数料" + ChatColor.AQUA
						+ " < The charge of your portal. > ");

				// 利用された回数の更新
				portal_info.increment_frequency();

//				if (CREATER.isOnline()) {
//
//					// ポータル運営者に手数料を送金
//					economy.depositPlayer(CREATER, CHARGE);
//
//					CREATER.sendMessage(ChatColor.WHITE + player.getName()
//							+ ChatColor.LIGHT_PURPLE + " から " + ChatColor.WHITE
//							+ event.getPortal().name + ChatColor.LIGHT_PURPLE
//							+ " の手数料 " + ChatColor.WHITE + CHARGE + "＄"
//							+ ChatColor.LIGHT_PURPLE + " を手に入れた！");
//					CREATER.sendMessage(ChatColor.AQUA + "< You got "
//							+ ChatColor.WHITE + CHARGE + "＄ " + ChatColor.AQUA
//							+ "by " + ChatColor.WHITE + player.getName()
//							+ ChatColor.AQUA + " for charge of "
//							+ ChatColor.WHITE + event.getPortal().name
//							+ ChatColor.AQUA + " >");
//				}

			} else {
				player.sendMessage(ChatColor.RED + "所持金が足りない！ "
						+ ChatColor.AQUA + "< Need more money to teleport! >");
				event.setCancelled(true);
			}

		}
		// 何か持っている時はテレポートを中止し、テレポーターの詳細を出力する
		else {
			event.setCancelled(true);

			player.sendMessage(ChatColor.LIGHT_PURPLE + "------- ポータルの詳細 "
					+ ChatColor.AQUA + "< Detail of the portal >"
					+ ChatColor.LIGHT_PURPLE + "-------");
			player.sendMessage(ChatColor.GREEN + "ポータル名 " + ChatColor.AQUA
					+ "< Portal Name >" + ChatColor.GREEN + " : "
					+ ChatColor.WHITE + event.getPortal().name);
			player.sendMessage(ChatColor.GREEN + "ポータル経営者 " + ChatColor.AQUA
					+ "< Creater >" + ChatColor.GREEN + " : " + ChatColor.WHITE
					+ CREATER.getName());
			player.sendMessage(ChatColor.GREEN + "利用された回数 " + ChatColor.AQUA
					+ "< Frequency >" + ChatColor.GREEN + " : "
					+ ChatColor.WHITE + FREQUENCY);
			player.sendMessage(ChatColor.GREEN + "コメント " + ChatColor.AQUA
					+ "< Comment >" + ChatColor.GREEN + " : " + ChatColor.WHITE
					+ COMMENT);
			player.sendMessage(ChatColor.GREEN + "運賃 " + ChatColor.AQUA
					+ "< Fare >" + ChatColor.GREEN + " : " + ChatColor.WHITE
					+ FARE);
			player.sendMessage(ChatColor.GREEN + "手数料 " + ChatColor.AQUA
					+ "< Charge >" + ChatColor.GREEN + " : " + ChatColor.WHITE
					+ CHARGE);
			player.sendMessage(ChatColor.GREEN + "合計 " + ChatColor.AQUA
					+ "< Total Fee >" + ChatColor.GREEN + " : "
					+ ChatColor.WHITE + TOTAL_FEE);

			player.sendMessage(ChatColor.LIGHT_PURPLE
					+ "------------------------------------------------------");

		}
	}

	// プレイヤーが実際にテレポートした後のイベント
	@EventHandler
	public void PlayerTeleportedEvent(WarpPortalsTeleportEvent event) {
		event.getPlayer()
				.getWorld()
				.playSound(event.getPlayer().getLocation(),
						Sound.PORTAL_TRAVEL, 20, 5);
		event.getPlayer().addPotionEffect(
				new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
		event.getPlayer().addPotionEffect(
				new PotionEffect(PotionEffectType.SLOW, 100, 1));
	}

}
