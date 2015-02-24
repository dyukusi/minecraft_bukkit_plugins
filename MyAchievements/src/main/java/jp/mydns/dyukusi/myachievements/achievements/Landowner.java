package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class Landowner extends AchieveInterface {
	OfflineDepositor depositor;

	public Landowner(OfflineDepositor depositor) {
		super("Landowner", Material.FENCE_GATE);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "土地を購入");
		lore_list.add(ChatColor.AQUA + "< Buy a land >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player,OnlineSession session) {
		if (player.hasMetadata("BuyLand")) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 1000, "Buy a land");
	}

	@Override
	public int getInvIndex() {
		return 15;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
