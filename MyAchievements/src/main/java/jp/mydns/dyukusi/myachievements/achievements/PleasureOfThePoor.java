package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class PleasureOfThePoor extends AchieveInterface {
	OfflineDepositor depositor;
	Economy economy;

	public PleasureOfThePoor(OfflineDepositor depositor, Economy economy) {

		super("Preasure of the poor", Material.GOLD_ORE);

		this.economy = economy;
		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "500＄貯金");
		lore_list.add(ChatColor.AQUA + "< Save 500$ >");

		setLore(lore_list);

	}

	@Override
	public boolean isAchieved(Player player,OnlineSession session) {

		// craft level equal to 2 or more
		if (economy.getBalance(player) >= 500) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Save 500$");
	}

	@Override
	public int getInvIndex() {
		return 18;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
