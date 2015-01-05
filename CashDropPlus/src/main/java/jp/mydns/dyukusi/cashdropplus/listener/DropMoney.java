package jp.mydns.dyukusi.cashdropplus.listener;

import java.util.Map;

import jp.mydns.dyukusi.cashdropplus.CashDropPlus;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
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
		if (event.getEntity() instanceof LivingEntity) {
			Entity monster = event.getEntity();

			if (event.getEntity().getKiller() instanceof Player) {
				Player player = (Player) event.getEntity().getKiller();
				int base_reward = plugin.get_base_reward(monster.getType());

				Map<Enchantment, Integer> ench = player.getItemInHand().getEnchantments();
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

	void message_reward(Player player, Entity monster, int reward) {
		player.sendMessage(plugin.get_prefix() + ChatColor.GOLD + " +" + reward + "$");
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.5F, 1.2F);
	}

}
