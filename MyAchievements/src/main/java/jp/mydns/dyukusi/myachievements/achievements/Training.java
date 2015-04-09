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

public class Training extends AchieveInterface {
	OfflineDepositor depositor;

	public Training(OfflineDepositor depositor) {
		super("Training", Material.WOOD_SWORD);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "レベル１０になる");
		lore_list.add(ChatColor.AQUA + "< Advance to level 10 >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (player.getLevel() >= 10) {
			return true;
		}
		
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Advance to level 10");
	}

	@Override
	public int getInvIndex() {
		return 1;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
