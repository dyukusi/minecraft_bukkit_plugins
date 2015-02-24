package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class EliteCrafter extends AchieveInterface {
	OfflineDepositor depositor;
	CraftLevel craftlevel;
	AchieveInterface before;

	public EliteCrafter(OfflineDepositor depositor, CraftLevel craftlevel,
			AchieveInterface before) {
		super("EliteCrafter", Material.WORKBENCH);

		this.depositor = depositor;
		this.craftlevel = craftlevel;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "クラフトレベルが35");
		lore_list.add(ChatColor.AQUA + "< Achieve craft level 35 >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		// craft level equal to 2 or more
		if (craftlevel.get_player_crafting_level_info(player).get_level() >= 35) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 600, "Achieved craft level 35");
	}

	@Override
	public int getInvIndex() {
		return 12;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
