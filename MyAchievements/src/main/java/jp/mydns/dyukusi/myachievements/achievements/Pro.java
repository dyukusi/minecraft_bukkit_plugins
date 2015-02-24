package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class Pro extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public Pro(OfflineDepositor depositor, AchieveInterface before) {
		super("General", Material.RECORD_8);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "1000時間プレイした");
		lore_list.add(ChatColor.AQUA + "< Reach 1000 hours of playtime >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		// sec
		Long sec = (Long) session.getPlayerTotals().getValue(
				PlayerVariable.TOTAL_PLAYTIME_RAW);

		Long min = (sec / 60);

		Long hour = (min / 60);

		if (hour >= 1000) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 10000,
				"Reach 1000 hours of playtime");
	}

	@Override
	public int getInvIndex() {
		return 49;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
