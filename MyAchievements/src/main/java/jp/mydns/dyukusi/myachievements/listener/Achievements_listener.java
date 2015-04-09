package jp.mydns.dyukusi.myachievements.listener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.MyAchievements;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;
import jp.mydns.dyukusi.myachievements.task.BiomeAchievementCheck;
import jp.mydns.dyukusi.myachievements.task.CheckAchieveTask;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.wolvencraft.yasp.StatisticsAPI;
import com.wolvencraft.yasp.session.OnlineSession;

public class Achievements_listener implements Listener {

	MyAchievements plugin;
	MyStat mystat;
	LinkedList<AchieveInterface> ach_list;

	public Achievements_listener(MyAchievements myAchievements, MyStat mystat,
			LinkedList<AchieveInterface> achievement_list) {
		this.plugin = myAchievements;
		this.mystat = mystat;
		this.ach_list = achievement_list;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	void CraftIronSword(CraftItemEvent event) {

		Player player = (Player) event.getWhoClicked();

		// success to craft iron sword
		if (!event.isCancelled()) {

			if (event.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
				player.setMetadata("craft_iron_sword", new FixedMetadataValue(
						plugin, true));
			}
		}

	}

	@EventHandler
	void WitherSkeletonDeath(EntityDeathEvent event) {
		if (event.getEntity().getKiller() instanceof Player) {

			Player player = event.getEntity().getKiller();

			if (event.getEntity() instanceof Skeleton) {

				ItemStack skull = null;

				for (ItemStack item : event.getDrops()) {
					if (item.getType().equals(Material.SKULL_ITEM)) {
						skull = item;
						break;
					}
				}

				if (skull != null) {
					// is wither skull
					if (skull.getDurability() == (short) 1) {
						player.setMetadata("get_wither_skull",
								new FixedMetadataValue(plugin, true));
					}
				}

			}
		}

	}

	@EventHandler
	void PlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		OnlineSession session = StatisticsAPI.getSession(player);

		// count got achievements total num
		int ach_num = 0;
		for (AchieveInterface ach : ach_list) {
			if (ach.hasAchievement(player)) {
				ach_num++;
			}
		}
		player.setMetadata("ach_total", new FixedMetadataValue(plugin, ach_num));

		new CheckAchieveTask(plugin, player, session)
				.runTaskTimerAsynchronously(plugin, 60, 80);

		// not registered yet
		if (!mystat.get_biome_map().containsKey(player.getName())) {
			mystat.get_biome_map().put(player.getName(),
					new HashMap<String, Boolean>());
		}

		new BiomeAchievementCheck(plugin, player, mystat.get_biome_map().get(
				player.getName()), mystat).runTaskTimer(plugin, 40, 40);

	}

	@EventHandler
	void KillElderGuardian(EntityDeathEvent event) {
		// kill the elder guardian
		if (event.getEntityType().equals(EntityType.GUARDIAN)) {

			Guardian guardian = (Guardian) event.getEntity();

			if (guardian.isElder()) {

				Player killer = event.getEntity().getKiller();

				if (killer != null) {
					killer.setMetadata("kill_elderguardian",
							new FixedMetadataValue(plugin, true));
				}
			}
		}

	}

	@EventHandler
	void KillEnderDragon(EntityDeathEvent event) {
		// kill the ender dragon
		if (event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			Player killer = event.getEntity().getKiller();

			if (killer != null) {
				killer.setMetadata("kill_enderdragon", new FixedMetadataValue(
						plugin, true));
			}
		}
	}

	@EventHandler
	void KillGhast(EntityDeathEvent event) {

		// kill Ghast in Nether
		if (event.getEntityType().equals(EntityType.GHAST)
				&& event.getEntity().getWorld().getEnvironment()
						.equals(Environment.NETHER)) {
			Player killer = event.getEntity().getKiller();

			if (!killer.hasMetadata("ghast")) {
				killer.setMetadata("ghast", new FixedMetadataValue(plugin, 0));
			}

			killer.setMetadata("ghast", new FixedMetadataValue(plugin, killer
					.getMetadata("ghast").get(0).asInt() + 1));
		}
	}

	@EventHandler
	void KillTheWither(EntityDeathEvent event) {
		// kill the wither
		if (event.getEntityType().equals(EntityType.WITHER)) {
			Player killer = event.getEntity().getKiller();

			if (killer != null) {
				killer.setMetadata("kill_wither", new FixedMetadataValue(
						plugin, true));
			}
		}
	}

	@EventHandler
	void KillByPunch(EntityDeathEvent event) {
		if (event.getEntity() instanceof Monster) {
			Monster monster = (Monster) event.getEntity();
			Player killer = monster.getKiller();

			if (killer != null) {

				// by punch?
				if (killer.getItemInHand().getType().equals(Material.AIR)) {

					// not registered yet
					if (!mystat.get_kill_punch_map().containsKey(
							killer.getName())) {
						mystat.get_kill_punch_map().put(killer.getName(), 0);
					}

					// increment kill count
					mystat.get_kill_punch_map()
							.put(killer.getName(),
									mystat.get_kill_punch_map().get(
											killer.getName()) + 1);

				}
			}
		}
	}

	@EventHandler
	void Fishing(PlayerFishEvent event) {
		if (event.getState().equals(State.CAUGHT_FISH)) {

			Player player = event.getPlayer();
			ItemStack fish = ((Item) event.getCaught()).getItemStack();

			// is clownfish?
			if (fish.getDurability() == (short) 2) {
				player.setMetadata("clownfish", new FixedMetadataValue(plugin,
						true));
			}
		}
	}

}
