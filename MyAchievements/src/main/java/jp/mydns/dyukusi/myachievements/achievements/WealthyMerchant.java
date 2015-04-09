package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

public class WealthyMerchant extends AchieveInterface {
	OfflineDepositor depositor;
	MyStat mystat;

	public WealthyMerchant(OfflineDepositor depositor, MyStat mystat) {
		super("Wealthy merchant", Material.GOLDEN_APPLE);

		this.depositor = depositor;
		this.mystat = mystat;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "計10000$分アイテムを売る");
		lore_list.add(ChatColor.AQUA
				+ "< Get 10000$ in total by selling item >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		
		if(mystat == null)
			player.sendMessage("nulllllllllllllll");
		
		if (mystat.get_sell_total_map().containsKey(player.getName())) {
			int sell_total = mystat.get_sell_total_map().get(player.getName());

			if (sell_total >= 10000) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 3000,
				"Get 10000$ in total by selling item");
	}

	@Override
	public int getInvIndex() {
		return 42;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
