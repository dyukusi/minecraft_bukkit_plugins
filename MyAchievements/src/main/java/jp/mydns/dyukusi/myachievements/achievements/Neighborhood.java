package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import com.wolvencraft.yasp.session.OnlineSession;

public class Neighborhood extends AchieveInterface {
	OfflineDepositor depositor;

	public Neighborhood(OfflineDepositor depositor) {
		super("Neighborhood", Material.LEATHER_BOOTS);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "3種類のバイオームに訪れる");
		lore_list.add(ChatColor.AQUA + "< Visit 3 kinds of biome >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (player.hasMetadata("visit_biome_num")) {

			int visit_biome_num = player.getMetadata("visit_biome_num").get(0)
					.asInt();

			if (visit_biome_num >= 3) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Visit 3 kinds of biome");
	}

	@Override
	public int getInvIndex() {
		return 45;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
