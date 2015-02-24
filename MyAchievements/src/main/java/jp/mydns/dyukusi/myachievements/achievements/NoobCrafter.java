package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class NoobCrafter extends AchieveInterface {
	OfflineDepositor depositor;
	CraftLevel craftlevel;

	public NoobCrafter(OfflineDepositor depositor, CraftLevel craftlevel) {
		super("NoobCrafter", Material.WORKBENCH);

		this.depositor = depositor;
		this.craftlevel = craftlevel;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "クラフトレベルが2");
		lore_list.add(ChatColor.AQUA + "< Achieve craft level 2 >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		// craft level equal to 2 or more
		if (craftlevel.get_player_crafting_level_info(player).get_level() >= 2) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Achieved craft level 2");
	}

	@Override
	public int getInvIndex() {
		return 9;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
