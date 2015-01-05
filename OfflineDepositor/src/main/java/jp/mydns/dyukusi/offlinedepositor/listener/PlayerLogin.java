package jp.mydns.dyukusi.offlinedepositor.listener;

import java.util.HashMap;
import java.util.LinkedList;

import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import jp.mydns.dyukusi.offlinedepositor.logindeposit.LoginDepositProcess;
import jp.mydns.dyukusi.offlinedepositor.value.DepositInformation;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLogin implements Listener {
	OfflineDepositor plugin;
	Economy economy;
	private static HashMap<String, LinkedList<DepositInformation>> depositor;

	public PlayerLogin(OfflineDepositor od,
			HashMap<String, LinkedList<DepositInformation>> d, Economy eco) {
		this.plugin = od;
		this.depositor = d;
		this.economy = eco;
	}

	@EventHandler
	void PlayerLogin(final PlayerLoginEvent event) {
		if (check_deposit(event.getPlayer().getName())) {
			final LinkedList<DepositInformation> list = depositor.get(event
					.getPlayer().getName());

			new BukkitRunnable() {
		
				public void run() {
					event.getPlayer().sendMessage(
							ChatColor.LIGHT_PURPLE + "[Deposit] "
									+ ChatColor.WHITE + list.size()
									+ ChatColor.GREEN
									+ " 件のあなたへの送金をサーバーが預かっています。");
					event.getPlayer().sendMessage(
							ChatColor.LIGHT_PURPLE + "[Deposit] "
									+ ChatColor.WHITE + list.size()
									+ ChatColor.AQUA
									+ " remittance for you is now available.");

				}
			}.runTaskLater(plugin, 40);

			int i = 0;
			for (DepositInformation di : list) {
				new LoginDepositProcess(plugin, economy, di, event.getPlayer())
						.runTaskLater(plugin, (i * 30) + 80);
				i++;
			}

			depositor.put(event.getPlayer().getName(),
					new LinkedList<DepositInformation>());

		}

	}

	// 預金メソッド
	public static void deposit(String deposit_from, String deposit_to,
			double amount, String reason) {

		// 登録済み
		if (depositor.containsKey(deposit_to)) {
			depositor.get(deposit_to).add(
					new DepositInformation(deposit_from, deposit_to, amount,
							reason));
		}
		// 未登録
		else {
			LinkedList<DepositInformation> list = new LinkedList<DepositInformation>();
			list.add(new DepositInformation(deposit_from, deposit_from, amount,
					reason));
			depositor.put(deposit_to, list);
		}

	}

	// 自分宛ての送金があるか確認
	boolean check_deposit(String name) {
		if (depositor.containsKey(name)) {
			LinkedList<DepositInformation> dpinfo = depositor.get(name);

			if (dpinfo.size() > 0)
				return true;
			else
				return false;

		} else {
			return false;
		}
	}

}
