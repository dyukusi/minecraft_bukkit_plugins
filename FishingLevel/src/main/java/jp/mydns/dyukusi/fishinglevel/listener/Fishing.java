package jp.mydns.dyukusi.fishinglevel.listener;

import java.util.Calendar;
import java.util.Random;

import jp.mydns.dyukusi.fishinglevel.CaughtItem;
import jp.mydns.dyukusi.fishinglevel.FishingCondition;
import jp.mydns.dyukusi.fishinglevel.FishingLevel;
import jp.mydns.dyukusi.fishinglevel.playerdata.PlayerFishingLevelData;
import jp.mydns.dyukusi.seasonalfood.SeasonalFood;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class Fishing implements Listener {

	FishingLevel plugin;
	SeasonalFood seasonalfood;

	public Fishing(FishingLevel fishingLevel, SeasonalFood seasonalfood) {
		this.plugin = fishingLevel;
		this.seasonalfood = seasonalfood;
	}

	@EventHandler
	void FishingEvent(PlayerFishEvent event) {

		Player player = event.getPlayer();

		String area_name = player.getMetadata("area").get(0).asString();

		if (area_name.contains("城郭都市")) {
			player.sendMessage(ChatColor.RED + "都市周辺には魚は生息していないようだ。");
			player.sendMessage(ChatColor.AQUA
					+ "< It seems there is no fishs near the city. >");
			player.sendMessage(ChatColor.RED + "城郭都市近郊より離れた池なら釣りに適しているはずだ。");
			player.sendMessage(ChatColor.AQUA
					+ "< It's better to go away from near fortified city for fishing. >");
			event.setCancelled(true);
			return;
		}

		Biome biome = player.getWorld().getBiome(
				player.getLocation().getBlockX(),
				player.getLocation().getBlockZ());

		// biome , season , day(per7day)
		int seed = biome.ordinal()
				+ seasonalfood.get_current_season().ordinal()
				+ (Calendar.DAY_OF_YEAR / 7);

		FishingCondition condition = FishingCondition.values()[seed
				% FishingCondition.values().length];

		switch (event.getState()) {

		// throw thread
		case FISHING:
			player.sendMessage(ChatColor.BLUE
					+ "-------------------------------------------------------");

			PlayerFishingLevelData data = plugin.get_playerfishleveldata().get(
					player.getName());

			player.sendMessage("FishingLv: " + ChatColor.GOLD
					+ data.get_level() + ChatColor.WHITE + "  Exp: "
					+ ChatColor.GOLD + data.get_exp() + ChatColor.WHITE + "/"
					+ plugin.get_required_exp_from_level(data.get_level()));
			player.sendMessage(ChatColor.GOLD + ""
					+ condition.get_items().length + ChatColor.WHITE
					+ "種類のアイテムの気配がする。" + ChatColor.AQUA + " < I can feel "
					+ condition.get_items().length
					+ " kinds of items in this place. >");
			player.sendMessage("あなたの識別可能範囲: "
					+ ChatColor.GOLD
					+ (100 - this.get_identify_range(data.get_level()) + "% 〜 100%")
					+ ChatColor.AQUA + " < Your identifier range. >");

			String str;

			int idx = 0;
			while (idx < condition.get_items().length) {
				CaughtItem item = condition.get_items()[idx++];

				str = this.create_prob_message(
						item.get_item().getType().name(), (int) item
								.get_probability(), plugin
								.get_playerfishleveldata()
								.get(player.getName()));

				if (idx < condition.get_items().length) {
					item = condition.get_items()[idx++];
					str += this.create_prob_message(item.get_item().getType()
							.name(), (int) item.get_probability(), data);
				}

				player.sendMessage(str);
			}
			player.sendMessage(ChatColor.BLUE
					+ "-------------------------------------------------------");

			break;

		case CAUGHT_FISH:
			PlayerFishingLevelData who = plugin.get_playerfishleveldata().get(
					player.getName());

			// reduce food level
			player.setFoodLevel(player.getFoodLevel() - 3);

			// caught item
			int lottery[] = new int[100];
			int index = 0;

			for (int item_index = 0; item_index < condition.get_items().length; item_index++) {
				CaughtItem item = condition.get_items()[item_index];

				for (int i = 0; i < item.get_probability()
						&& index < lottery.length; i++) {
					lottery[index++] = item_index;
				}

			}

			int monster_prob = (int) ((50.0 - (who.get_level()) / 2.0));
			// int monster_prob = 100;
			Random rand = new Random();

			// monster
			if (Math.abs(rand.nextInt() % 100) <= monster_prob) {

				player.sendMessage(ChatColor.RED + "モンスターを釣り上げてしまった！"
						+ ChatColor.AQUA + "< Caught a monster! Run! >");
				((Item) event.getCaught()).getItemStack()
						.setType(Material.DIRT);
				((Item) event.getCaught()).getItemStack().setDurability(
						(short) 0);

				EntityType monsters[] = { EntityType.ZOMBIE,
						EntityType.CREEPER, EntityType.ZOMBIE,
						EntityType.ZOMBIE, EntityType.SKELETON,
						EntityType.SKELETON, EntityType.SKELETON,
						EntityType.SKELETON, EntityType.SKELETON,
						EntityType.WITCH, EntityType.SQUID,
						EntityType.GUARDIAN, EntityType.PIG_ZOMBIE,
						EntityType.CAVE_SPIDER, EntityType.BLAZE,
						EntityType.BAT };

				EntityType spawn_monster = monsters[Math.abs(rand.nextInt()
						% monsters.length)];

				int max_height = 8;
				int spawn_y, i;
				for (i = 1; i < max_height; i++) {
					if (!player.getLocation().add(0, i, 0).getBlock().getType()
							.equals(Material.AIR)) {
						break;
					}
				}

				spawn_y = player.getLocation().getBlockY() + i;

				// int spawn_y = player.getLocation().getBlockY() + 8;
				// boolean already_decided = false;
				// int decide_y = 0;

				// for (int i = spawn_y; player.getLocation().getBlockY() < i;
				// i--) {
				// if (!already_decided) {
				// Block block = player.getWorld().getBlockAt(
				// player.getLocation().getBlockX(), i,
				// player.getLocation().getBlockZ());
				// if (block.getType().equals(Material.AIR)
				// && player
				// .getWorld()
				// .getBlockAt(
				// block.getLocation().add(0, -1,
				// 0)).getType()
				// .equals(Material.AIR)) {
				// decide_y = i;
				// already_decided = true;
				// }
				// }
				//
				// // obstacle
				// if (!player
				// .getWorld()
				// .getBlockAt(player.getLocation().getBlockX(), i,
				// player.getLocation().getBlockZ()).getType()
				// .equals(Material.AIR)) {
				// already_decided = false;
				// }
				// }
				//
				// if (!already_decided) {
				// decide_y = player.getLocation().getBlockY();
				// }

				Location spawn_loc = player.getLocation();
				spawn_loc.setY(spawn_y);

				LivingEntity monster = (LivingEntity) player.getWorld()
						.spawnEntity(spawn_loc, spawn_monster);

				if (monster instanceof Zombie || monster instanceof Skeleton) {
					monster.getEquipment().setHelmet(
							new ItemStack(Material.IRON_HELMET));
					monster.getEquipment().setHelmetDropChance((float) 0);
				}

			}
			// item
			else {
				CaughtItem caught = condition.get_items()[lottery[Math.abs(rand
						.nextInt() % lottery.length)]];

				// add exp to player
				int exp_amount = get_exp_amount((int) caught.get_probability());
				who.add_exp(exp_amount);

				// change item
				((Item) event.getCaught()).getItemStack().setType(
						caught.get_item().getType());
				((Item) event.getCaught()).getItemStack().setDurability(
						caught.get_item().getDurability());

				player.sendMessage(this.get_item_name_color((int) caught
						.get_probability())
						+ caught.get_item().getType().name()
						+ ChatColor.WHITE
						+ "を釣り上げた！ "
						+ ChatColor.AQUA
						+ "< You caught "
						+ caught.get_item().getType().name()
						+ "! >  "
						+ ChatColor.GREEN + "+" + exp_amount + "exp");
				player.playSound(player.getLocation(), Sound.WATER, 1, 1);

				// judge level up
				who.level_up_or_not(plugin, player,
						plugin.get_required_exp_from_level(who.get_level()));

			}

			break;

		case CAUGHT_ENTITY:
			// player.sendMessage("state : CAUGHT_ENTITY");

			break;

		default:
			// player.sendMessage("state : DEFAULT");

			break;
		}

	}

	private int get_exp_amount(int probability) {
		double prob_d = (double) (101 - probability) / 100.0;

		return (int) Math.pow(400.0, prob_d);
	}

	private String create_prob_message(String item_name, int probability,
			PlayerFishingLevelData player_fishing_data) {
		int level = player_fishing_data.get_level();

		String item_name_c;
		String prob_c;

		if (this.get_can_identify_or_not(level, probability)) {
			item_name_c = this.get_item_name_color(probability) + item_name;
			prob_c = ChatColor.GREEN + Integer.toString(probability) + "%";
		} else {
			item_name_c = ChatColor.RED + item_name.replaceAll("[A-Z|_]", "?");
			prob_c = ChatColor.DARK_RED + "??%";
		}

		return String.format("%-40s", ChatColor.BOLD + "- " + item_name_c
				+ ChatColor.WHITE + "(" + prob_c + ChatColor.WHITE + ")");
	}

	private boolean get_can_identify_or_not(int level, int probability) {

		if ((100 - this.get_identify_range(level)) <= probability) {
			return true;
		}

		return false;
	}

	private int get_identify_range(int level) {
		return (int) (8.65 * (Math.log(level) / Math.log(1.4)));
	}

	private ChatColor get_item_name_color(int probability) {

		if (20 <= probability) {
			return ChatColor.WHITE;
		} else if (5 <= probability) {
			return ChatColor.LIGHT_PURPLE;
		} else {
			return ChatColor.YELLOW;
		}

	}

}
