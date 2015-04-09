package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.db.data.DataStore.DataStoreType;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class TouristAttraction extends AchieveInterface {
	OfflineDepositor depositor;
	MyStat mystat;

	public TouristAttraction(OfflineDepositor depositor, MyStat mystat) {
		super("Tourist attraction", Material.COMPASS);

		this.depositor = depositor;
		this.mystat = mystat;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "キノコ群生島、メガタイガ、メサ、樹氷の森に訪れる");
		lore_list.add(ChatColor.AQUA + "< Visit Mushroom Island, Mega Taiga,");
		lore_list.add(ChatColor.AQUA + "Mesa, Ice Plains Spike >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		HashMap<String, Boolean> map = mystat.get_biome_map().get(
				player.getName());

		if (map.containsKey(Biome.MUSHROOM_ISLAND.name())
				&& map.containsKey(Biome.MEGA_TAIGA.name())
				&& map.containsKey(Biome.MESA.name())
				&& map.containsKey(Biome.ICE_PLAINS_SPIKES.name())) {
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
		return 33;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return false;
	}
}
