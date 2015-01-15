package jp.mydns.dyukusi.itemsounds.listener;

import java.util.HashMap;

import jp.mydns.dyukusi.itemsounds.ItemSounds;
import jp.mydns.dyukusi.itemsounds.soundinfo.SoundInformation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

	ItemSounds plugin;

	public InventoryListener(ItemSounds iss, HashMap<Material, SoundInformation> it_map) {
		this.plugin = iss;
	}

	@EventHandler
	void SelectItemEvent(InventoryClickEvent event) {
		Material item = event.getCursor().getType();
		HumanEntity he = event.getWhoClicked();

		if (he instanceof Player) {
			Player player = (Player) he;
	

			// アイテムを置いた時
			if (!item.equals(Material.AIR)) {
				SoundInformation sinfo;

				// equip armor slot
				if (event.getSlotType().equals(SlotType.ARMOR)) {

					// defined item
					if (plugin.iscontain_equip_armor_sound(item)) {
						sinfo = plugin.get_equip_armor_sound_inf(item);
					}
					// undefined item
					else {
						sinfo = plugin.get_default_equip_armor_sound_inf();
					}

				}
				// other slot
				else {
					// defined item
					if (plugin.iscontain_put_sound(item)) {
						sinfo = plugin.get_put_sound_inf(item);
					}
					// undefined item
					else {
						sinfo = plugin.get_default_item_sound_inf();
					}
				}

				// play sound
				player.playSound(player.getLocation(), sinfo.get_sound(), sinfo.get_volume(), sinfo.get_pitch());

			}
			// dragging item
			else {
				player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
			}

		}

	}

	@EventHandler
	void SwitchItemEvent(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItem(event.getNewSlot());

		if (item != null) {
			Material material = item.getType();
			SoundInformation sinfo;

			// isdefined in config
			if (plugin.iscontain_handitem_sound(material)) {
				sinfo = plugin.get_handitem_sound_inf(material);
			}
			// undefined
			else {
				sinfo = plugin.get_default_handitem_sound_inf();
			}

			player.getWorld().playSound(player.getLocation(), sinfo.get_sound(), sinfo.get_volume(), sinfo.get_pitch());
		}
	}

}
