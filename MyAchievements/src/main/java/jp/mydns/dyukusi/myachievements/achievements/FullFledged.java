package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.db.data.DataStore.DataStoreType;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class FullFledged extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public FullFledged(OfflineDepositor depositor, AchieveInterface before) {
		super("Full fledged", Material.FEATHER);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "実績を計２３個以上獲得する");
		lore_list.add(ChatColor.AQUA + "< Get 23 achievements >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (before.hasAchievement(player)) {
			if (player.hasMetadata("ach_total")) {

				if (player.getMetadata("ach_total").get(0).asInt() >= 23) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 500, "Craft iron sword");
	}

	@Override
	public int getInvIndex() {
		return 4;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		if (before.hasAchievement(player)) {
			return true;
		}
		return false;
	}
}
