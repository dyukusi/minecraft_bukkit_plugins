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

public class WoodCrafter extends AchieveInterface {
	OfflineDepositor depositor;
	CraftLevel craftlevel;
	AchieveInterface before;

	public WoodCrafter(OfflineDepositor depositor, CraftLevel craftlevel,
			AchieveInterface before) {
		super("WoodCrafter", Material.WORKBENCH);

		this.depositor = depositor;
		this.craftlevel = craftlevel;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "クラフトレベルが7");
		lore_list.add(ChatColor.AQUA + "< Achieve craft level 7 >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		// craft level equal to 2 or more
		if (craftlevel.get_player_crafting_level_info(player).get_level() >= 7) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 200, "Achieved craft level 7");
	}

	@Override
	public int getInvIndex() {
		return 10;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
