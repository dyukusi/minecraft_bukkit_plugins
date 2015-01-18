package jp.mydns.dyukusi.cashdropplus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mydns.dyukusi.cashdropplus.listener.DropMoney;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CashDropPlus extends JavaPlugin {
	private Economy economy = null;
	private String prefix = ChatColor.YELLOW + "[CashDropPlus]"
			+ ChatColor.WHITE;
	private HashMap<String, Integer> base_reward_map;
	private HashMap<Enchantment, HashMap<Integer, Integer>> ench_effect_map;

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
			getLogger().info("creating config.yml file...");
			this.saveDefaultConfig();
		}

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// base reward
		this.base_reward_map = new HashMap<String, Integer>();
		List<String> list = this.getConfig().getStringList("base_reward");

		for (String str : list) {
			String array[] = str.split(",");
			// EntityType type = EntityType.valueOf(array[0]);
			int exp = Integer.parseInt(array[1]);
			base_reward_map.put(array[0], exp);
		}

		// enchantment effect
		this.ench_effect_map = new HashMap<Enchantment, HashMap<Integer, Integer>>();
		list = this.getConfig().getStringList("enchant_effect");

		for (String str : list) {
			String array[] = str.split(",");
			Enchantment ench = Enchantment.getByName(array[0]);
			int level = Integer.parseInt(array[1]);
			int effect = Integer.parseInt(array[2]);

			if (ench_effect_map.containsKey(ench)) {
				ench_effect_map.get(ench).put(level, effect);
			} else {
				ench_effect_map.put(ench, new HashMap<Integer, Integer>());
				ench_effect_map.get(ench).put(level, effect);
			}
		}

		// listener register
		this.getServer().getPluginManager()
				.registerEvents(new DropMoney(this, this.economy), this);
	}

	@Override
	public void onDisable() {

	}

	boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			this.economy = economyProvider.getProvider();
		}
		return (economy != null);
	}

	public int get_base_reward(String type) {
		if (this.base_reward_map.containsKey(type))
			return this.base_reward_map.get(type);
		else {
			return 0;
		}
	}

	public String get_prefix() {
		return this.prefix;
	}

	public double get_ench_effect(Map<Enchantment, Integer> ench) {

		int result = 100;

		for (java.util.Map.Entry<Enchantment, Integer> ent : ench.entrySet()) {
			Enchantment enchant = ent.getKey();
			int level = ent.getValue();

			if (this.ench_effect_map.containsKey(enchant)
					&& this.ench_effect_map.get(enchant).containsKey(level)) {
				int temp = this.ench_effect_map.get(enchant).get(level);
				if (result < temp) {
					result = temp;
				}
			}
		}

		return (double) result / 100.0;
	}
}
