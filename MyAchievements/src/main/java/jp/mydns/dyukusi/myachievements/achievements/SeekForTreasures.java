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

public class SeekForTreasures extends AchieveInterface {
	OfflineDepositor depositor;
	AchieveInterface before;

	public SeekForTreasures(OfflineDepositor depositor, AchieveInterface before) {
		super("Seek for treasures", Material.STONE_PICKAXE);

		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "300ブロック破壊");
		lore_list.add(ChatColor.AQUA + "< Break 300 blocks >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if ((Integer) session.getPlayerTotals().getValue(
				PlayerVariable.BLOCKS_BROKEN) > 300) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 300, "Break 300 blocks");
	}

	@Override
	public int getInvIndex() {
		return 28;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return before.hasAchievement(player);
	}
}
