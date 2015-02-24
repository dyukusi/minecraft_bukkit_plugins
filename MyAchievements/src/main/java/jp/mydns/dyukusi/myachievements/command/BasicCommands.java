package jp.mydns.dyukusi.myachievements.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;

import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.MyAchievements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BasicCommands implements CommandExecutor, Listener {

	MyAchievements plugin;
	LinkedList<AchieveInterface> list;
	String inv_name = "実績 <Achievements>";

	public BasicCommands(MyAchievements myAchievements,
			LinkedList<AchieveInterface> achievement_list) {
		this.plugin = myAchievements;
		this.list = achievement_list;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (command.getName().equals("ac")) {

				if (args.length == 0) {

					Inventory inv = Bukkit
							.createInventory(player, 54, inv_name);

					// add unworked yet icon
					ItemStack unworked = new ItemStack(Material.BARRIER, 1);
					ItemMeta unw_meta = unworked.getItemMeta();
					unw_meta.setDisplayName(ChatColor.RED
							+ "作成中 <Under construction>");
					unworked.setItemMeta(unw_meta);
					inv.setItem(1, unworked);

					// add wall
					ItemStack wall = new ItemStack(Material.STAINED_GLASS_PANE,
							1, (short) 15);
					ItemMeta wall_meta = wall.getItemMeta();
					wall_meta.setDisplayName(ChatColor.BLACK + "|");
					wall.setItemMeta(wall_meta);
					for (int i = 14; i < 53; i += 9) {
						inv.setItem(i, wall);
					}

					// add achievements to achinv
					for (AchieveInterface ent : list) {
						ItemStack ach;
						ItemMeta meta;

						// has the achievement
						if (ent.hasAchievement(player)) {
							ach = new ItemStack(ent.getIcon(), 1);
							meta = ach.getItemMeta();

							meta.setDisplayName(ent.getColor() + ent.getName());
							meta.setLore(ent.getLore());
						}
						// not has the achievement
						else {
							// display part of achievement info
							if (ent.isDisplayInfo(player)) {
								ach = new ItemStack(
										Material.STAINED_GLASS_PANE, 1,
										(short) 5);
								meta = ach.getItemMeta();

								meta.setDisplayName(ChatColor.RED + "?????");

								meta.setLore(ent.getLore());
							}
							// hide achievement info
							else {
								ach = new ItemStack(
										Material.STAINED_GLASS_PANE, 1,
										(short) 14);
								meta = ach.getItemMeta();

								meta.setDisplayName(ChatColor.RED + "?????");

								List<String> lore = new ArrayList<String>();
								lore.add(ChatColor.DARK_PURPLE + "?????");

								meta.setLore(lore);
							}

						}

						ach.setItemMeta(meta);
						inv.setItem(ent.getInvIndex(), ach);

					}

					player.openInventory(inv);
				} else if (args.length == 1) {

					if (args[0].equals("reset")) {

						for (AchieveInterface ent : list) {
							player.performCommand("ca remove " + ent.getName()
									+ " " + player.getName());
						}

						player.sendMessage(ChatColor.GREEN
								+ "Reset all achievements completed!");
						return true;
					}

				}

			}
		}

		return false;
	}

	@EventHandler
	void ClickItemInCustomInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inv = event.getInventory();
		if (inv.getName().equals(inv_name)) {
			event.setCancelled(true);
		}
	}

}
