package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class Sergeant extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public Sergeant(OfflineDepositor depositor, AchieveInterface before) {
		super("Sergeant", Material.RECORD_5);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "100時間プレイした");
		lore_list.add(ChatColor.AQUA + "< Reach 100 hours of playtime >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		// sec
		Long sec = (Long) session.getPlayerTotals().getValue(
				PlayerVariable.TOTAL_PLAYTIME_RAW);

		Long min = (sec / 60);

		Long hour = (min / 60);

		if (hour >= 48) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 1500, "Reach 100 hours of playtime");
	}

	@Override
	public int getInvIndex() {
		return 16;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
