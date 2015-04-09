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

public class PhantomFish extends AchieveInterface {
	OfflineDepositor depositor;

	public PhantomFish(OfflineDepositor depositor) {
		super("Phantom fish", Material.RAW_FISH);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "クマノミを釣る");
		lore_list.add(ChatColor.AQUA + "< Fish the clownfish >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		if (player.hasMetadata("clownfish")) {
			return true;
		}
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 1500, "Fish the clownfish");
	}

	@Override
	public int getInvIndex() {
		return 52;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
