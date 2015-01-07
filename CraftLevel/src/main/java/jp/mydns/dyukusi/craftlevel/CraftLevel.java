package jp.mydns.dyukusi.craftlevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import jp.mydns.dyukusi.craftlevel.command.BasicCommands;
import jp.mydns.dyukusi.craftlevel.level.PlayerCraftLevelData;
import jp.mydns.dyukusi.craftlevel.listener.CraftingItem;
import jp.mydns.dyukusi.craftlevel.listener.PlayerLogin;
import jp.mydns.dyukusi.craftlevel.requireinfo.RequirementInformation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CraftLevel extends JavaPlugin {
	private String prefix = ChatColor.GREEN + "[CraftLevel]" + ChatColor.WHITE;
	public String character_data_path = getDataFolder().getAbsolutePath() + "/characterlevel.bin";
	private HashMap<Material, RequirementInformation> requirements;
	private HashMap<UUID, PlayerCraftLevelData> player_crafting_level;
	private HashMap<Material, Integer> experience;
	private int next_level_exp[];
	private int minimum_success_rate, maximum_success_rate, increase_rate, max_craft_level;
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
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml").exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		// Config
		no_requirements_data_error = getConfig().getBoolean("no_requirements_data_error");
		minimum_success_rate = getConfig().getInt("minimum_success_rate");
		maximum_success_rate = getConfig().getInt("maximum_success_rate");
		increase_rate = getConfig().getInt("increase_rate");
		max_craft_level = getConfig().getInt("max_craft_level");

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

		// require_level, material, maximum_success_rate0~100% (option)
		requirements = new HashMap<Material, RequirementInformation>();
		Material material;
		int require_level;
		int custom_maximum_success_rate;
		for (String str : getConfig().getStringList("requirements")) {
			custom_maximum_success_rate = -1;

			array = str.split(",");
			require_level = Integer.parseInt(array[0]);
			material = Material.getMaterial(array[1]);

			if (array.length >= 3) {
				custom_maximum_success_rate = Integer.parseInt(array[2]);
				getLogger().info(
						"############################################################################################################"
								+ custom_maximum_success_rate);
			}

			requirements
					.put(material, new RequirementInformation(material, require_level, custom_maximum_success_rate));
		}

		// exp, material
		experience = new HashMap<Material, Integer>();
		int exp;
		for (String str : getConfig().getStringList("experience")) {
			array = str.split(",");
			exp = Integer.parseInt(array[0]);
			material = Material.getMaterial(array[1]);

			experience.put(material, exp);
		}

		// get player craft level config
		if (new File(character_data_path).exists()) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(new FileInputStream(character_data_path));
				this.player_crafting_level = (HashMap<UUID, PlayerCraftLevelData>) objinput.readObject();
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
			getLogger().info("characterlevel.bin was not found. Creating new data...");
			player_crafting_level = new HashMap<UUID, PlayerCraftLevelData>();
		}

		// register listeners
		this.getServer().getPluginManager().registerEvents(new CraftingItem(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);

		// register commands
		getCommand("cl").setExecutor(new BasicCommands(this));
	}

	@Override
	public void onDisable() {
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(new FileOutputStream(character_data_path));
			objoutput.writeObject(this.player_crafting_level);
			objoutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double get_success_rate(int level, Material material) {
		RequirementInformation reqinf = get_require_info(material);
		int require_level;
		require_level = reqinf.get_require_level();

		int success_rate = this.minimum_success_rate;
		if (level >= require_level) {
			success_rate += increase_rate + (increase_rate * (level - require_level));
		}

		int maximum_success_rate;

		// this.getServer().broadcastMessage("reqinf custom maximum rate : " +
		// reqinf.get_maximum_success_rate());

		// default rate
		if (reqinf.get_maximum_success_rate() < 0) {
			maximum_success_rate = this.get_maximum_success_rate();
		} else {
			maximum_success_rate = reqinf.get_maximum_success_rate();
		}

		// default maximum success rate 0-100 %
		if (success_rate > maximum_success_rate) {
			success_rate = maximum_success_rate;
		} else if (success_rate < 0) {
			success_rate = 0;
		}

		// 0 ~ 1.0
		return (double) success_rate / 100;
	}

	public HashMap<Material, RequirementInformation> get_requirements() {
		return this.requirements;
	}

	public PlayerCraftLevelData get_player_crafting_level_info(Player player) {
		return this.player_crafting_level.get(player.getUniqueId());
	}

	public boolean get_player_crafting_level_info_contains(Player player) {
		return this.player_crafting_level.containsKey(player.getUniqueId());
	}

	public void put_new_player_to_crafting_level_info(Player player) {
		this.player_crafting_level.put(player.getUniqueId(), new PlayerCraftLevelData(player));
	}

	public int[] get_next_level_exp() {
		return this.next_level_exp;
	}

	public int get_minimum_success_rate() {
		return this.minimum_success_rate;
	}

	public int get_maximum_success_rate() {
		return this.maximum_success_rate;
	}

	public int get_increase_rate() {
		return this.increase_rate;
	}

	public int get_max_craft_level() {
		return this.max_craft_level;
	}

	public int get_experience(Material material) {
		return this.experience.get(material);
	}

	public int calc_success_exp(Recipe recipe) {
		int exp = 0;
		List<Material> contents = new ArrayList<Material>();

		// ShapedRecipe
		if (recipe instanceof ShapedRecipe) {
			ShapedRecipe sr = (ShapedRecipe) recipe;
			for (ItemStack item : sr.getIngredientMap().values()) {
				if (item != null)
					contents.add(item.getType());
			}
		}
		// ShapelessRecipe
		else if (recipe instanceof ShapelessRecipe) {
			ShapelessRecipe sl = (ShapelessRecipe) recipe;
			for (ItemStack item : sl.getIngredientList()) {
				if (item != null)
					contents.add(item.getType());
			}
		}
		// undefined instance
		else {
			this.getServer().broadcastMessage(
					this.get_prefix() + " undefined recipe instance. please tell Dyukusi to fix this bug.");
			return 0;
		}

		for (Material material : contents) {

			if (this.experience.containsKey(material)) {
				exp += this.experience.get(material);
			} else {
				this.getServer().broadcastMessage(
						this.get_prefix() + ChatColor.RED + material.toString()
								+ " has been not supported yet. need to add this item into config file.");
				exp = 0;

			}
		}

		return exp;
	}

	public RequirementInformation get_require_info(Material material) {
		if (this.requirements.containsKey(material))
			return this.requirements.get(material);
		else {
			if (this.no_requirements_data_error) {
				getServer().broadcastMessage(
						this.get_prefix() + material.name()
								+ " doesn't have requirements data. please contact server owner to fix");
			}
			return new RequirementInformation(material, 1, -1);
		}
	}

	public String get_prefix() {
		return this.prefix;
	}

}
