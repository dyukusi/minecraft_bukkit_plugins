package jp.mydns.dyukusi.offlinedepositor.logindeposit;

import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import jp.mydns.dyukusi.offlinedepositor.value.DepositInformation;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginDepositProcess extends BukkitRunnable {

	OfflineDepositor plugin;
	Economy economy;
	Player to;
	DepositInformation di;

	public LoginDepositProcess(OfflineDepositor p, Economy eco,
			DepositInformation d, Player player) {
		this.plugin = p;
		this.economy = eco;
		this.di = d;
		this.to = player;
	}

	public void run() {	
		economy.depositPlayer(to, di.get_amount());
		to.sendMessage(ChatColor.LIGHT_PURPLE + "[Deposit] " + ChatColor.WHITE
				+ (int)di.get_amount() + "$" + ChatColor.GREEN + " from "
				+ ChatColor.WHITE + di.get_from() + ChatColor.GREEN + " for "
				+ ChatColor.WHITE + di.get_reason() + ChatColor.GREEN + " on "
				+ ChatColor.WHITE + di.get_time_str());
		to.playSound(to.getLocation(), Sound.ANVIL_USE, 1, 1);
	}

}
