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

public class GoldMania extends AchieveInterface {
	OfflineDepositor depositor;

	public GoldMania(OfflineDepositor depositor) {
		super("Gold mania", Material.GOLD_CHESTPLATE);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "金防具一式を装備");
		lore_list.add(ChatColor.AQUA
				+ "< Equip all gold armors at the same time >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {
		try {
			if (player.getEquipment().getHelmet().getType()
					.equals(Material.GOLD_HELMET)
					&& player.getEquipment().getChestplate().getType()
							.equals(Material.GOLD_CHESTPLATE)
					&& player.getEquipment().getLeggings().getType()
							.equals(Material.GOLD_LEGGINGS)
					&& player.getEquipment().getBoots().getType()
							.equals(Material.GOLD_BOOTS)) {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 1000,
				"Equip all gold armors at the same time");
	}

	@Override
	public int getInvIndex() {
		return 37;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
