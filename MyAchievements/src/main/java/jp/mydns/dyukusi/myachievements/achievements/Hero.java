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

public class Hero extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public Hero(OfflineDepositor depositor, AchieveInterface before) {
		super("Hero", Material.DIAMOND_SWORD);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.AQUA);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "エンダードラゴン討伐");
		lore_list.add(ChatColor.AQUA + "< Kill the ender dragon >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		if (player.hasMetadata("kill_enderdragon")) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 10000, " Kill the ender dragon");
	}

	@Override
	public int getInvIndex() {
		return 8;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		if (before.hasAchievement(player)) {
			return true;
		}
		return false;
	}
}
