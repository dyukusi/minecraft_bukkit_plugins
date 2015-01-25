package jp.mydns.dyukusi.myplugin.listener;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.myplugin.MyPlugin;
import jp.mydns.dyukusi.myplugin.task.FireProtection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerEffect implements Listener {

	MyPlugin plugin;
	CraftLevel cl;

	public PlayerEffect(MyPlugin myPlugin, CraftLevel craftlevel) {
		this.plugin = myPlugin;
		this.cl = craftlevel;
	}

	@EventHandler
	void DrinkPotion(PlayerItemConsumeEvent event) {
		new FireProtection(plugin, event.getPlayer()).runTaskLater(plugin, 5);
	}

	@EventHandler
	void SplashPotion(PotionSplashEvent event) {

		if (event.getEntity() instanceof Player) {
			new FireProtection(plugin, (Player) event.getEntity())
					.runTaskLater(plugin, 5);
		}
	}

	@EventHandler
	void EntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntityType().equals(EntityType.VILLAGER)) {

			if (event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				Location loc = event.getEntity().getLocation();
				PlayerCraftLevelData pinfo = cl
						.get_player_crafting_level_info(damager);

				if (pinfo.get_level() > 25) {

					plugin.getServer().broadcastMessage(
							damager.getName() + ChatColor.RED
									+ "は村人に危害を加えている。 " + ChatColor.DARK_RED
									+ "[" + loc.getBlockX() + ","
									+ loc.getBlockY() + "," + loc.getBlockZ()
									+ "]");
					plugin.getServer().broadcastMessage(
							ChatColor.AQUA + "< " + ChatColor.WHITE
									+ event.getEntityType().name()
									+ ChatColor.AQUA + " was damaged by "
									+ ChatColor.WHITE
									+ event.getDamager().getName()
									+ ChatColor.AQUA + " >");

				} else {
					plugin.getServer()
							.broadcastMessage(
									damager.getName()
											+ ChatColor.RED
											+ "は村人に危害を加えようとしたが、CraftLevelが足りないためキャンセルされた。 "
											+ ChatColor.DARK_RED + "["
											+ loc.getBlockX() + ","
											+ loc.getBlockY() + ","
											+ loc.getBlockZ() + "]");
					plugin.getServer()
							.broadcastMessage(
									ChatColor.AQUA
											+ "< "
											+ ChatColor.WHITE
											+ event.getEntityType().name()
											+ ChatColor.AQUA
											+ " was tried to damage  "
											+ ChatColor.WHITE
											+ event.getDamager().getName()
											+ ChatColor.AQUA
											+ " but cancelled because of lack of CraftLevel >");
					event.setCancelled(true);
				}

			}else{
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	void EntityDamage(EntityDamageEvent event) {
		if (event.getEntityType().equals(EntityType.VILLAGER)) {

			if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
								
			} else {
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	void CreatureKilled(EntityDeathEvent event) {
		// killed by player
		if (event.getEntityType().equals(EntityType.CREEPER)) {
			if (event.getEntity().getKiller() instanceof Player) {
				final Entity p = event.getEntity();

				final Double x = p.getLocation().getX();
				final Double y = p.getLocation().getY();
				final Double z = p.getLocation().getZ();
				p.getWorld().playSound(p.getLocation(), Sound.FUSE, 3F, 1F);
				plugin.getServer().getScheduler()
						.runTask(plugin, new Runnable() {
							public void run() {
								p.getWorld().createExplosion(x, y, z, 1F, true,
										false);
							}
						});
			}
		}
	}

	@EventHandler
	void CraftItem(CraftItemEvent event) {

		Material type = event.getCurrentItem().getType();

		if (type.equals(Material.STONE_SWORD)) {
			ItemStack item = event.getCurrentItem();
			ItemMeta meta = item.getItemMeta();

		}
	}

	// 海底神殿用
	@EventHandler
	void MonsterSpawn(CreatureSpawnEvent event) {

		if (event.getEntityType().equals(EntityType.GUARDIAN)) {
			if (event.isCancelled()) {
				event.setCancelled(false);
			}
		}
	}

	// y30以下松明禁止,着火禁止
	@EventHandler(priority = EventPriority.HIGHEST)
	void BlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Material material = block.getType();

		// 松明
		if (material.equals(Material.TORCH)
				&& block.getLocation().getBlockY() <= 30) {
			block.setType(Material.AIR);
			Item item = block.getWorld().dropItemNaturally(block.getLocation(),
					new ItemStack(Material.STICK));
			block.getWorld().playSound(block.getLocation(), Sound.FIZZ, 0.8F,
					1.5F);
			event.getPlayer().sendMessage(
					ChatColor.RED + "空気が薄すぎる！" + ChatColor.AQUA
							+ " < Lack of oxygen! >");
		}
		// ジャック・オ・ランタン
		else if (material.equals(Material.JACK_O_LANTERN)
				&& block.getLocation().getBlockY() <= 30) {
			block.setType(Material.PUMPKIN);
			block.getWorld().playSound(block.getLocation(), Sound.FIZZ, 0.8F,
					1.5F);
			event.getPlayer().sendMessage(
					ChatColor.RED + "空気が薄すぎる！" + ChatColor.AQUA
							+ " < Lack of oxygen! >");
		}

	}

	// y30以下ではネザーラックに着火禁止
	@EventHandler(priority = EventPriority.HIGHEST)
	void Ignite(BlockIgniteEvent event) {
		Block block = event.getBlock().getWorld()
				.getBlockAt(event.getBlock().getLocation().add(0, -1, 0));
		Location location = block.getLocation();

		if (event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {

			if (block.getType().equals(Material.NETHERRACK)) {

				if (location.getBlockY() <= 30) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(
							ChatColor.RED + "空気が薄すぎる！" + ChatColor.AQUA
									+ " < Lack of oxygen! >");

				}
			}
		}

	}

	//
	// @EventHandler
	// void BlockGrow(BlockGrowEvent event) {
	// Block block = event.getBlock();
	//
	// }
	//
	// @EventHandler
	// void CanBuild(BlockCanBuildEvent event) {
	// plugin.getServer().broadcastMessage(
	// event.getBlock().getType() + "が設置するよ");
	//
	// }
	//
	// @EventHandler
	// void StructureGrow(StructureGrowEvent event) {
	// for (BlockState blockstate : event.getBlocks()) {
	// plugin.getServer().broadcastMessage(
	// blockstate.toString() + "が成長(structure)");
	// }
	// }

}