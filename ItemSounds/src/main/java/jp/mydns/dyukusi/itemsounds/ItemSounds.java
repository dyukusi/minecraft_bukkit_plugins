package jp.mydns.dyukusi.itemsounds;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jp.mydns.dyukusi.itemsounds.listener.InventoryListener;
import jp.mydns.dyukusi.itemsounds.soundinfo.SoundInformation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class ItemSounds extends JavaPlugin {

	String item_sound_list_path;
	private HashMap<Material, SoundInformation> equip_armor_map;
	private HashMap<Material, SoundInformation> itemsound_map;
	private HashMap<Material, SoundInformation> handitem_sound_map;
	private SoundInformation default_equip_armor_sound;
	private SoundInformation default_handitem_sound;
	private SoundInformation default_item_sound;
	@Override
	public void onEnable() {

		// mcstats
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.RED + "Failed to submit the stats");
			// Failed to submit the stats
		}

		// config
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml").exists()) {
			getLogger().info("Creating config.yml ...");
			this.saveDefaultConfig();
		}

		// default equip armor sound
		String armor[] = this.getConfig().getString("default_equip_armor_sound").split(",");
		default_equip_armor_sound = new SoundInformation(Sound.valueOf(armor[0]), Float.parseFloat(armor[1]),
				Float.parseFloat(armor[2]));

		// equip armor sounds list
		List<String> setting_list = this.getConfig().getStringList("equip_armor_sounds");
		equip_armor_map = new HashMap<Material, SoundInformation>();

		for (String str : setting_list) {
			String array[] = str.split(",");
			Material material = Material.valueOf(array[0]);
			Sound sound = Sound.valueOf(array[1]);
			float volume = Float.parseFloat(array[2]);
			float pitch = Float.parseFloat(array[3]);
	
			equip_armor_map.put(material, new SoundInformation(sound, volume, pitch));
		}

		// default equip handitem sound
		String equip[] = this.getConfig().getString("default_equip_handitem_sound").split(",");
		default_handitem_sound = new SoundInformation(Sound.valueOf(equip[0]), Float.parseFloat(equip[1]),
				Float.parseFloat(equip[2]));

		// equip item sounds
		setting_list = this.getConfig().getStringList("equip_handitem_sounds");
		handitem_sound_map = new HashMap<Material, SoundInformation>();

		for (String str : setting_list) {
			String array[] = str.split(",");
			Material material = Material.valueOf(array[0]);
			Sound sound = Sound.valueOf(array[1]);
			float volume = Float.parseFloat(array[2]);
			float pitch = Float.parseFloat(array[3]);

			handitem_sound_map.put(material, new SoundInformation(sound, volume, pitch));
		}

		// default put item sound
		String undefined[] = this.getConfig().getString("default_put_item_sound").split(",");
		default_item_sound = new SoundInformation(Sound.valueOf(undefined[0]), Float.parseFloat(undefined[1]),
				Float.parseFloat(undefined[2]));

		// put_item_sounds
		setting_list = this.getConfig().getStringList("put_item_sounds");
		itemsound_map = new HashMap<Material, SoundInformation>();

		for (String str : setting_list) {
			String array[] = str.split(",");
			Material material = Material.valueOf(array[0]);
			Sound sound = Sound.valueOf(array[1]);
			float volume = Float.parseFloat(array[2]);
			float pitch = Float.parseFloat(array[3]);

			itemsound_map.put(material, new SoundInformation(sound, volume, pitch));
		}

		this.getServer().getPluginManager().registerEvents(new InventoryListener(this, itemsound_map), this);
	}

	@Override
	public void onDisable() {

	}

	public boolean iscontain_put_sound(Material material) {
		return this.itemsound_map.containsKey(material);
	}

	public boolean iscontain_handitem_sound(Material material) {
		return this.handitem_sound_map.containsKey(material);
	}

	public boolean iscontain_equip_armor_sound(Material material) {
		return this.equip_armor_map.containsKey(material);
	}

	public SoundInformation get_put_sound_inf(Material material) {
		return this.itemsound_map.get(material);
	}

	public SoundInformation get_handitem_sound_inf(Material material) {
		return this.handitem_sound_map.get(material);
	}

	public SoundInformation get_equip_armor_sound_inf(Material material) {
		return this.equip_armor_map.get(material);
	}

	public SoundInformation get_default_equip_armor_sound_inf() {
		return this.default_equip_armor_sound;
	}

	public SoundInformation get_default_item_sound_inf() {
		return this.default_item_sound;
	}

	public SoundInformation get_default_handitem_sound_inf() {
		return this.default_handitem_sound;
	}
}
