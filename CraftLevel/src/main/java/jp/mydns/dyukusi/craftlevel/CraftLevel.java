package jp.mydns.dyukusi.craftlevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jp.mydns.dyukusi.craftlevel.command.BasicCommands;
import jp.mydns.dyukusi.craftlevel.config.Message;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.listener.CraftingItem;
import jp.mydns.dyukusi.craftlevel.listener.PlayerLogin;
import jp.mydns.dyukusi.craftlevel.materialinfo.MaterialInfo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CraftLevel extends JavaPlugin {
	private static String prefix = ChatColor.GREEN + "[CraftLevel]"
			+ ChatColor.WHITE;
	public String character_data_path = getDataFolder().getAbsolutePath()
			+ "/characterlevel.bin";

	private HashMap<UUID, PlayerCraftLevelData> player_crafting_level;
	private static HashMap<Material, MaterialInfo> material_info;
	private static HashMap<Message, String> message;

	private static int next_level_exp[];
	private static int minimum_success_rate, maximum_success_rate,
			increase_rate, max_craft_level;
	boolean no_requirements_data_error;

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
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		no_requirements_data_error = getConfig().getBoolean(
				"no_requirements_data_error");
		minimum_success_rate = getConfig().getInt("minimum_success_rate");
		maximum_success_rate = getConfig().getInt("maximum_success_rate");
		increase_rate = getConfig().getInt("increase_rate");
		max_craft_level = getConfig().getInt("max_craft_level");

		message = new HashMap<Message, String>();
		// Message
		Set<String> keys = getConfig().getConfigurationSection("message")
				.getKeys(false);

		for (String key : keys) {
			String value = getConfig().getConfigurationSection("message")
					.getString(key);

			message.put(Message.valueOf(key), value);
		}

		String array[];

		// next_level
		List<String> next_level_list = getConfig().getStringList("next_level");
		next_level_exp = new int[next_level_list.size() + 1];
		int level, require_exp;
		for (String str : getConfig().getStringList("next_level")) {
			array = str.split(",");
			level = Integer.parseInt(array[0]);
			require_exp = Integer.parseInt(array[1]);
			next_level_exp[level] = require_exp;
		}

		this.material_info = new HashMap<Material, MaterialInfo>();
		List<String> minfo_list = getConfig().getStringList("material_info");

		for (String data : minfo_list) {
			MaterialInfo info = this.decode_material_info_str(data);
			this.material_info.put(info.get_material(), info);
		}

		// get player craft level config
		if (new File(character_data_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(
						new FileInputStream(character_data_path));
				this.player_crafting_level = (HashMap<UUID, PlayerCraftLevelData>) objinput
						.readObject();
				objinput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		// no save data
		else {
			getLogger().info(
					"characterlevel.bin was not found. Creating new data...");
			player_crafting_level = new HashMap<UUID, PlayerCraftLevelData>();
		}

		// register listeners
		this.getServer().getPluginManager()
				.registerEvents(new CraftingItem(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new PlayerLogin(this), this);

		// register commands
		getCommand("cl").setExecutor(new BasicCommands(this));

	}

	@Override
	public void onDisable() {
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(character_data_path));
			objoutput.writeObject(this.player_crafting_level);
			objoutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PlayerCraftLevelData get_player_crafting_level_info(Player player) {
		if (this.player_crafting_level.containsKey(player.getUniqueId())) {
			return this.player_crafting_level.get(player.getUniqueId());
		} else {
			
			player.sendMessage(ChatColor.RED+" You haven't CraftLevel data. Creating new data...");
			this.player_crafting_level.put(player.getUniqueId(),
					new PlayerCraftLevelData(player));

			return this.player_crafting_level.get(player.getUniqueId());
		}
	}

	public boolean get_player_crafting_level_info_contains(Player player) {
		return this.player_crafting_level.containsKey(player.getUniqueId());
	}

	public void put_new_player_to_crafting_level_info(Player player) {
		this.player_crafting_level.put(player.getUniqueId(),
				new PlayerCraftLevelData(player));
	}

	static public int[] get_next_level_exp() {
		return next_level_exp;
	}

	static public int get_minimum_success_rate() {
		return minimum_success_rate;
	}

	static public int get_maximum_success_rate() {
		return maximum_success_rate;
	}

	static public int get_increase_rate() {
		return increase_rate;
	}

	static public int get_max_craft_level() {
		return max_craft_level;
	}

	public MaterialInfo decode_material_info_str(String str) {
		// Material, RequireLevel, ExperienceAsMaterial, Options

		String[] data = str.split(",");

		Material material = Material.valueOf(data[0]);
		int require_level = Integer.parseInt(data[1]);
		int experience_as_material = Integer.parseInt(data[2]);

		int max_success_rate = get_maximum_success_rate();
		int min_success_rate = get_minimum_success_rate();
		int fixed_success_rate = -1;
		int custom_craft_experience = -1;
		// # Options( n = Integer )
		// # MAR:n -> Max Success Rate. Not limited by default max success rate.
		// # MIR:n -> Minimum Success Rate. Not limited by default minimum
		// success rate.
		// # FSR:n -> Fixed Success Rate. Not craft level based.
		// # CEX:n -> Custom Craft Experience. Not material based.

		// decode options
		if (!data[3].equals("none")) {

			for (int i = 3; i < data.length; i++) {
				String[] split = data[i].split(":");
				String option = split[0];
				int n = Integer.parseInt(split[1]);

				switch (option) {
				case "MAR":
					max_success_rate = n;
					break;
				case "MIR":
					min_success_rate = n;
					break;
				case "FSR":
					fixed_success_rate = n;
					break;
				case "CEX":
					custom_craft_experience = n;
					break;
				case "none":
					break;
				default:
					getLogger().info(split[0] + " is not difined as Option.");
					break;
				}
			}

		}

		return new MaterialInfo(material, require_level, max_success_rate,
				min_success_rate, fixed_success_rate, experience_as_material,
				custom_craft_experience);
	}

	public static String get_prefix() {
		return prefix;
	}

	public static MaterialInfo get_material_info(Material material) {

		if (material_info.containsKey(material)) {
			return material_info.get(material);
		} else {
			return null;
		}

	}

	public static String get_message(Message msg) {

		if (message.containsKey(msg)) {
			return prefix + " " + message.get(msg);

		}
		// not difined
		else {
			return ChatColor.RED + "Not difined message.";
		}
	}

}
