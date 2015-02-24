package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class UnsatisfiedYet extends AchieveInterface {
	OfflineDepositor depositor;
	Economy economy;
	AchieveInterface before;

	public UnsatisfiedYet(OfflineDepositor depositor, Economy economy,
			AchieveInterface before) {
		super("Unsatisfied yet", Material.GOLD_INGOT);

		this.economy = economy;
		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "10000＄貯金");
		lore_list.add(ChatColor.AQUA + "< Save 10000$ >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (economy.getBalance(player) >= 10000) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 2000, "Save 3000$");
	}

	@Override
	public int getInvIndex() {
		return 21;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
