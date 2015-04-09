package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.MyAchievements;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.wolvencraft.yasp.db.data.DataStore.DataStoreType;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class UnderTheSea extends AchieveInterface {
	OfflineDepositor depositor;

	public UnderTheSea(OfflineDepositor depositor) {
		super("Under the sea", Material.SEA_LANTERN);

		this.depositor = depositor;

		setColor(ChatColor.AQUA);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "エルダーガーディアン討伐");
		lore_list.add(ChatColor.AQUA + "< Kill the elder guardian >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		if (player.hasMetadata("kill_elderguardian")) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 3000, " Kill the elder guardian");
	}

	@Override
	public int getInvIndex() {
		return 44;
	}

	@Override
	public boolean isDisplayInfo(Player player) {

		return false;
	}
}
