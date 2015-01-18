package jp.mydns.dyukusi.cashdropplus.listener;

import java.util.Map;

import jp.mydns.dyukusi.cashdropplus.CashDropPlus;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DropMoney implements Listener {

	CashDropPlus plugin;
	Economy economy;

	public DropMoney(CashDropPlus CPS, Economy eco) {
		this.plugin = CPS;
		this.economy = eco;
	}

	@EventHandler
	void EntityKilled(EntityDeathEvent event) {

		// monster death
		if (event.getEntity() instanceof Monster) {
			Entity monster = event.getEntity();

			if (event.getEntity().getKiller() instanceof Player) {
				Player player = (Player) event.getEntity().getKiller();
				String monster_name;
				int base_reward;

				switch (monster.getType()) {

				// Normal creeper or Charged creeper?
				case CREEPER:
					monster_name = CreeperIdentifier((Creeper) monster);
					break;

				case GUARDIAN:
					monster_name = GuardianIdentifier((Guardian) monster);
					break;

				case SKELETON:
					monster_name = SkeletonIdentifier((Skeleton) monster);

				default:
					monster_name = monster.getType().name();
					break;
				}

				// get base reward
				base_reward = plugin.get_base_reward(monster_name);

				if (base_reward <= 0) {
					base_reward = 0;
					plugin.getServer().broadcastMessage(
							plugin.get_prefix() + " There is no "
									+ monster_name + "'s reward data.");
					plugin.getServer()
							.broadcastMessage(
									plugin.get_prefix()
											+ " Please configure config.yml file in plugin folder.");
				}

				Map<Enchantment, Integer> ench = player.getItemInHand()
						.getEnchantments();
				double ench_effect = plugin.get_ench_effect(ench);
				int result_reward = (int) (base_reward * ench_effect);
				message_reward(player, monster, result_reward);
				economy.depositPlayer(player, result_reward);
				// plugin.getServer().broadcastMessage(
				// event.getEntity().getType().name() + " は " +
				// event.getEntity().getKiller() + "に倒された！");

			}

		}
	}

	private String SkeletonIdentifier(Skeleton skeleton) {
		if (skeleton.getSkeletonType().equals(SkeletonType.NORMAL))
			return "SKELETON";
		else
			return "WITHER_SKELETON";
	}

	private String GuardianIdentifier(Guardian guardian) {
		// plugin.getServer().broadcastMessage("is elder guardian?");

		if (guardian.isElder()) {
			return "ELDER_GUARDIAN";
		}

		return "GUARDIAN";
	}

	String CreeperIdentifier(Creeper creeper) {
		// plugin.getServer().broadcastMessage("is charged creeper?");

		// is charged
		if (creeper.isPowered()) {
			return "CHARGED_CREEPER";
		}

		return "CREEPER";
	}

	void message_reward(Player player, Entity monster, int reward) {
		player.sendMessage(plugin.get_prefix() + ChatColor.GOLD + " +" + reward
				+ "$");
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.5F, 1.2F);
	}

}
