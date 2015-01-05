package jp.mydns.dyukusi.customdurability;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import jp.mydns.dyukusi.customdurability.command.SetDurabilityCommand;
import jp.mydns.dyukusi.listener.BlockBreakListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CustomDurability extends JavaPlugin {

	private String prefix = ChatColor.YELLOW + "[CustomDurability]";

	// tool -> block -> amount of durability decrease
	private HashMap<Material, HashMap<Material, Integer>> decrease_map;
	private double[] unbreaking_effect;

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

		// enchantment effect
		List<String> temp_unbreaking_list = getConfig().getStringList("unbreaking_effect");
		this.unbreaking_effect = new double[temp_unbreaking_list.size()];
		for (String str : temp_unbreaking_list) {
			String[] array = str.split(",");
			int index = Integer.parseInt(array[0]);
			int probability = Integer.parseInt(array[1]);
			this.unbreaking_effect[index] = (double) probability / 100.0;
		}

		this.decrease_map = new HashMap<Material, HashMap<Material, Integer>>();
		String durability = "durability_decrease";
		ConfigurationSection cs = getConfig().getConfigurationSection(durability);
		Set<String> tools = cs.getKeys(false);

		for (String tool : tools) {
			List<String> setting_list = getConfig().getStringList(durability + "." + tool);

			for (String setting : setting_list) {
				String array[] = setting.split(",");
				Material block = Material.valueOf(array[0]);
				int decrease = Integer.parseInt(array[1]);
				this.set_decrease(Material.valueOf(tool), block, decrease);
			}

		}

		// register listeners
		this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

		// command
		getCommand("cd").setExecutor(new SetDurabilityCommand(this));
	}

	@Override
	public void onDisable() {

	}

	public double get_unbreaking_effect(int level) {
		// out bound
		if (this.unbreaking_effect.length <= level)
			return 0;
		else
			return this.unbreaking_effect[level];
	}

	// return durability or -1
	public int get_decrease(Material tool, Material block) {
		if (this.decrease_map.containsKey(tool) && this.decrease_map.get(tool).containsKey(block)) {
			return this.decrease_map.get(tool).get(block);
		} else {
			return -1;
		}
	}

	private void set_decrease(Material tool, Material block, int decrease) {
		if (this.decrease_map.containsKey(tool)) {
			this.decrease_map.get(tool).put(block, decrease);
		} else {
			this.decrease_map.put(tool, new HashMap<Material, Integer>());
			this.decrease_map.get(tool).put(block, decrease);
		}
	}

	public String get_prefix() {
		return this.prefix;
	}

}
