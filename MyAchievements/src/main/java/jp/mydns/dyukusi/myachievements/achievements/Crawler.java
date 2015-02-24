package jp.mydns.dyukusi.myachievements.achievements;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;
import javax.security.auth.login.LoginContext;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class Crawler extends AchieveInterface {
	OfflineDepositor depositor;

	public Crawler(OfflineDepositor depositor) {
		super("Crawler", Material.LEATHER_BOOTS);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "500m歩く");
		lore_list.add(ChatColor.AQUA + "< Walk 500m >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		
		if ((double) session.getPlayerTotals().getValue(
				PlayerVariable.DISTANCE_FOOT) > 500) {
			return true;
 }

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Walk 500m");
	}

	@Override
	public int getInvIndex() {
		return 36;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
