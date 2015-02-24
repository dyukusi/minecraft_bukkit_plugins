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

public class AwakeToMine extends AchieveInterface {
	OfflineDepositor depositor;

	public AwakeToMine(OfflineDepositor depositor) {
		super("Awake to mine", Material.WOOD_PICKAXE);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "10ブロック破壊");
		lore_list.add(ChatColor.AQUA + "< Break 10 blocks >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if ((Integer) session.getPlayerTotals().getValue(
				PlayerVariable.BLOCKS_BROKEN) > 10) {
			return true;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 100, "Break 10 blocks");
	}

	@Override
	public int getInvIndex() {
		return 27;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
