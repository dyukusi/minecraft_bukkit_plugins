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

public class Hell extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public Hell(OfflineDepositor depositor, AchieveInterface before) {
		super("Hell", Material.PORTAL);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "地獄への門を開く");
		lore_list
				.add(ChatColor.AQUA
						+ "< Open the hell gate >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (player.hasMetadata("open_hell_gate")) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 3000, " Open the hell gate");
	}

	@Override
	public int getInvIndex() {
		return 5;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		if (before.hasAchievement(player)) {
			return true;
		}
		return false;
	}
}
