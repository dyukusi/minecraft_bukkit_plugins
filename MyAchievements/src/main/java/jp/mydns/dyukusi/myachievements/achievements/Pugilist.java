package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.db.data.DataStore.DataStoreType;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class Pugilist extends AchieveInterface {
	OfflineDepositor depositor;
	MyStat mystat;

	public Pugilist(OfflineDepositor depositor, MyStat mystat) {
		super("Pugilist", Material.SKULL);

		this.depositor = depositor;
		this.mystat = mystat;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "素手で敵を100体討伐する");
		lore_list.add(ChatColor.AQUA + "< Kill 100 monsters by punch. >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		// not registered yet
		if (!mystat.get_kill_punch_map().containsKey(player.getName())) {
			mystat.get_kill_punch_map().put(player.getName(), 0);
		}

		if (mystat.get_kill_punch_map().get(player.getName()) >= 100) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 2000, "Kill 100 monsters by punch");
	}

	@Override
	public int getInvIndex() {
		return 43;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
