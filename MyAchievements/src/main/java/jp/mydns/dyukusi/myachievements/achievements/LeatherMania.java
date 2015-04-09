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

public class LeatherMania extends AchieveInterface {
	OfflineDepositor depositor;

	public LeatherMania(OfflineDepositor depositor) {
		super("Leather mania", Material.LEATHER_CHESTPLATE);

		this.depositor = depositor;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "革防具一式を装備");
		lore_list.add(ChatColor.AQUA
				+ "< Equip all lether armors at the same time >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		try {
			if (player.getEquipment().getHelmet().getType()
					.equals(Material.LEATHER_HELMET)
					&& player.getEquipment().getChestplate().getType()
							.equals(Material.LEATHER_CHESTPLATE)
					&& player.getEquipment().getLeggings().getType()
							.equals(Material.LEATHER_LEGGINGS)
					&& player.getEquipment().getBoots().getType()
							.equals(Material.LEATHER_BOOTS)) {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 500,
				"Equip all lether armors at the same time");
	}

	@Override
	public int getInvIndex() {
		return 36;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		return true;
	}
}
