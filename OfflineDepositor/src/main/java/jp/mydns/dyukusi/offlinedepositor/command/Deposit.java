package jp.mydns.dyukusi.offlinedepositor.command;

import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import jp.mydns.dyukusi.offlinedepositor.listener.PlayerLogin;
import jp.mydns.dyukusi.offlinedepositor.logindeposit.LoginDepositProcess;
import jp.mydns.dyukusi.offlinedepositor.value.DepositInformation;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Deposit implements CommandExecutor {
	OfflineDepositor plugin;
	Economy economy;

	public Deposit(OfflineDepositor p, Economy eco) {
		this.plugin = p;
		this.economy = eco;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {
		// Example
		// /od deposit [PlayerName] [Amount(Double)] [Reason]

		if (cmd.getName().equals("od")) {

			if (args.length >= 3) {

				if (args[0].equals("deposit")) {

					OfflinePlayer to = plugin.getServer().getOfflinePlayer(
							args[1]);

					// System.out.println(to.getFirstPlayed()+" , "+to.getLastPlayed()+" , "+to.getgetPlayerTime()+" , "+
					// to.getPlayerTimeOffset());
					// プレイヤーが見つからなかった
					if (to.getFirstPlayed() == 0L) {
						sender.sendMessage(ChatColor.RED + args[1]
								+ "という名前のプレイヤーは存在しません。");
						return true;
					}

					double Amount = 0.0;

					try {
						Amount = Double.parseDouble(args[2]);
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED
								+ "金額は整数又は浮動小数点のみ受け付けます。");
						return true;
					}

					String Reason = "";
					for (int i = 3; i < args.length; i++)
						Reason += args[i] + " ";

					// オフライン時はサーバーが保存
					if (!to.isOnline()) {

						// MAPに登録
						PlayerLogin.deposit(sender.getName(), args[1], Amount,
								Reason);
					}
					// オンライン時はそのまま送金
					else {
						sender.sendMessage(ChatColor.LIGHT_PURPLE
								+ "[OfflineDepositor] " + ChatColor.WHITE
								+ to.getName() + ChatColor.GREEN
								+ " はオンラインなので、直ちに送金します。");
						sender.sendMessage(ChatColor.LIGHT_PURPLE
								+ "[OfflineDpositor]"
								+ ChatColor.AQUA
								+ "<"
								+ ChatColor.WHITE
								+ to.getName()
								+ ChatColor.AQUA
								+ " is online now, so remmitance is implemented immediately.>");

						new LoginDepositProcess(plugin, economy,
								new DepositInformation(sender.getName(), to
										.getName(), Amount, Reason),
								to.getPlayer()).runTaskLater(plugin, 40);

					}

					sender.sendMessage(ChatColor.LIGHT_PURPLE
							+ "[OfflineDepositor] " + ChatColor.GREEN
							+ "送金が完了しました！ " + ChatColor.AQUA
							+ "< Deposit is successful! > ");
					return true;

				}
			}

		}

		return false;
	}
}
