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

	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {
		// Example
		// /od deposit [PlayerName] [Amount(Double)] [Reason]

		if (cmd.getName().equals("od")) {

			if (args.length >= 3) {

				if (args[0].equals("deposit")) {

					OfflinePlayer to = plugin.getServer().getOfflinePlayer(
							args[1]);

					// プレイヤーが見つからなかった
					if (to.getFirstPlayed() == 0L) {
						sender.sendMessage(ChatColor.RED + args[1]
								+ "という名前のプレイヤーは存在しません。");
						sender.sendMessage(ChatColor.AQUA + "< "
								+ ChatColor.WHITE + args[1] + ChatColor.AQUA
								+ " doesn't exist in this server. >");
						return true;
					}

					double Amount = 0.0;

					try {
						Amount = Double.parseDouble(args[2]);
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED + "金額は整数値のみ受け付けます。");
						sender.sendMessage(ChatColor.AQUA
								+ "< Money amount must be integers. >");

						return true;
					}

					// lack of money to pay
					if (sender instanceof Player) {
						Player player = (Player) sender;
						int having_money = (int) economy.getBalance(player);

						if (having_money < Amount) {
							player.sendMessage(ChatColor.RED + "所持金が足りません。");
							player.sendMessage(ChatColor.AQUA
									+ "< Not enough money to pay. >");
							return true;
						}

					}

					String Reason = "";
					for (int i = 3; i < args.length; i++)
						Reason += args[i] + " ";

					//subtract player's having money
					if (sender instanceof Player) {
						economy.withdrawPlayer((Player) sender, Amount);
					}

					// deposit process
					plugin.deposit(sender, to, (int) Amount, Reason);

					return true;
				}
			}

		}

		return false;
	}
}
