package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class Adventurer extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public Adventurer(OfflineDepositor depositor, AchieveInterface before) {
		super("Adventurer", Material.IRON_BOOTS);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);
		setDisplayUnachieved(true);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "50種類のバイオームに訪れる");
		lore_list.add(ChatColor.AQUA + "< Visit 50 kinds of biome >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (player.hasMetadata("visit_biome_num")) {

			int visit_biome_num = player.getMetadata("visit_biome_num").get(0)
					.asInt();

			if (visit_biome_num >= 50) {
				return true;
			}
		}

		return false;
	}


	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 5000, "Visit 50 kinds of biome");
	}

	@Override
	public int getInvIndex() {
		return 48;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
