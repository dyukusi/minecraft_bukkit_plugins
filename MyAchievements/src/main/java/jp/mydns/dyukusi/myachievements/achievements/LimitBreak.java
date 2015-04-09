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

public class LimitBreak extends AchieveInterface {
	OfflineDepositor depositor;

	public LimitBreak(OfflineDepositor depositor) {
		super("Limit break", Material.GOLD_SWORD);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "ウィザーを倒す");
		lore_list.add(ChatColor.AQUA + "< Kill the wither >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (player.hasMetadata("kill_wither")) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 5000, "Kill the wither");
	}

	@Override
	public int getInvIndex() {
		return 53;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
