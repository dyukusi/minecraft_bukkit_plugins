package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginContext;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

import me.teej107.customachievement.CustomAchievement;

public class BeginningOfYourStory extends AchieveInterface {
	OfflineDepositor depositor;

	public BeginningOfYourStory(OfflineDepositor depositor) {
		super("Beginning of your story", Material.BOOK);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "初めてログイン");
		lore_list.add(ChatColor.AQUA + "< Login for the first time >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player,OnlineSession session) {
		return true;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Login for the first time");
	}

	@Override
	public int getInvIndex() {
		return 0;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
