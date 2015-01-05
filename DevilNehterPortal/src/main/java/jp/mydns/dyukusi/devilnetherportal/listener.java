package jp.mydns.dyukusi.devilnetherportal;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class listener implements Listener {
	DevilNetherPortal plugin;

	public listener(DevilNetherPortal nogate) {
		this.plugin = nogate;
	}

	@EventHandler
	public void EnterNetherPortal(PlayerPortalEvent event) {
		Player player = event.getPlayer();

		// Normal -> Nether
		if (event.getFrom().getWorld().getEnvironment().equals(Environment.NORMAL)
				&& event.getTo().getWorld().getEnvironment().equals(Environment.NETHER)) {

			plugin.getServer().broadcastMessage(
					ChatColor.RED + player.getName() + " は愚かにも自ら地獄へ足を踏み入れた！ " + ChatColor.AQUA + "< "
							+ player.getName() + " fall into the HELL.>");

			World to = event.getTo().getWorld();
			Chunk center = to.getChunkAt(event.getTo());

			// ゲートを封鎖するタスク
			new process(plugin, to, center).runTaskLater(plugin, 100);

			// 座標保存
			player.setMetadata("from_gate_point", new FixedMetadataValue(plugin, new from_point(player.getWorld(),
					player.getLocation())));

			// インベントリのブレイズロッドを消失させる処理
			if (player.getInventory().contains(Material.BLAZE_ROD)) {
				player.sendMessage(ChatColor.DARK_RED + "インベントリ内のブレイズロッドを全て失った！ " + ChatColor.AQUA
						+ "<You lose all blaze rod in your inventory!>");
				player.getInventory().remove(Material.BLAZE_ROD);
			}

		}
	}

	@EventHandler
	public void RemovePortalWhenFrameBroke(BlockBreakEvent event) {
		Block obsidian = event.getBlock();
		Material block_type = obsidian.getType();

		if (block_type.equals(Material.OBSIDIAN)) {
			// plugin.getServer().broadcastMessage(
			// "黒曜石が破壊されました。 " + obsidian.getLocation());
			break_portal_block(obsidian.getLocation());
		}
	}

	private void break_portal_block(Location location) {
		World world = location.getWorld();
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();

		Block block = world.getBlockAt(x, y, z);

		// ポータルブロックなら破壊
		if (block.getType().equals(Material.PORTAL)) {
			block.setType(Material.AIR);
			// plugin.getServer().broadcastMessage(
			// x + "," + y + "," + z + "  破壊しました.");
			block.getWorld().playSound(location, Sound.GLASS, 20, 5F);
		}

		// x:-1 y:0 z:0
		if (world.getBlockAt(x - 1, y, z).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x - 1, y, z));
		// x:1 y:0 z:0
		if (world.getBlockAt(x + 1, y, z).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x + 1, y, z));
		// x:0 y:0 z:-1
		if (world.getBlockAt(x, y, z - 1).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x, y, z - 1));
		// x:0 y:0 z:1
		if (world.getBlockAt(x, y, z + 1).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x, y, z + 1));
		// x:0 y:-1 z:0
		if (world.getBlockAt(x, y - 1, z).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x, y - 1, z));
		// x:0 y:1 z:0
		if (world.getBlockAt(x, y + 1, z).getType().equals(Material.PORTAL))
			break_portal_block(new Location(world, x, y + 1, z));

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void CreatePortal(PortalCreateEvent event) {

		if (!event.isCancelled()) {

			boolean announce = false;
			for (Block block : event.getBlocks()) {
				Location spawn1 = new Location(block.getWorld(), block.getX() + 2, block.getY() + 1, block.getZ() - 2);
				Location spawn2 = new Location(block.getWorld(), block.getX() - 2, block.getY() + 1, block.getZ() + 2);

				if (block.getLocation().getY() % 2 == 0)
					block.getWorld().spawnEntity(spawn1, EntityType.PIG_ZOMBIE);
				else
					block.getWorld().spawnEntity(spawn2, EntityType.SKELETON);

				block.getWorld().playSound(block.getLocation(), Sound.AMBIENCE_THUNDER, 20, 5F);

				// Fall Thunder!
				event.getWorld().strikeLightning(block.getLocation());
				event.getWorld().strikeLightningEffect(block.getLocation());

				if (!announce) {
					announce = true;
					String gate = "(" + block.getX() + "," + block.getY() + "," + block.getY() + ")";

					if (event.getWorld().getEnvironment().equals(Environment.NORMAL)) {
						plugin.getServer().broadcastMessage(
								ChatColor.RED + "(x,y,z) = " + gate + "に地獄へと続く扉が開かれた。 " + ChatColor.AQUA
										+ "< HELL GATE was opend at " + gate + " >");
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather thunder");
					}

					for (Player player : plugin.getServer().getOnlinePlayers()) {
						block.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 20, 5F);
					}
				}
			}
		}
	}

	@EventHandler
	void EntityPortal(EntityPortalEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	void PortalCreate(PortalCreateEvent event) {
		World world = event.getWorld();
		if (world.getEnvironment().equals(Environment.NETHER) && !event.isCancelled()) {
			// ゲートを封鎖するタスク
			new process(plugin, world, world.getChunkAt(event.getBlocks().get(0))).runTaskLater(plugin, 60);

		}
	}

	@EventHandler
	void BlockPlce(BlockPlaceEvent event) {
		Material material = event.getBlock().getType();

		if (event.getPlayer().getWorld().getEnvironment().equals(Environment.NETHER)) {
			if (material.equals(Material.ENDER_CHEST) || material.equals(Material.CHEST)
					|| material.equals(Material.TRAPPED_CHEST)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(plugin.get_prefix() + ChatColor.RED + " チェストが灼熱に耐えられない！");
				event.getPlayer().sendMessage(ChatColor.AQUA + "< Chests can't bear the scorching heat! >");
			}
		}
	}
}
