package jp.mydns.dyukusi.seasonalfood;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import jp.mydns.dyukusi.seasonalfood.command.BasicCommand;
import jp.mydns.dyukusi.seasonalfood.listener.BarrenDirt;
import jp.mydns.dyukusi.seasonalfood.listener.GrowEvent;
import jp.mydns.dyukusi.seasonalfood.listener.PlayerJoin;
import jp.mydns.dyukusi.seasonalfood.seasontype.SeasonType;
import jp.mydns.dyukusi.seasonalfood.task.SeasonChanger;
import jp.mydns.dyukusi.title.Title;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SeasonalFood extends JavaPlugin {

	private String prefix = ChatColor.YELLOW + "[SeasonalFood]"
			+ ChatColor.WHITE;
	private HashMap<Material, Double[]> ripe_rate_map;
	private SeasonType current_season;
	private boolean force_next_season;

	@Override
	public void onEnable() {

		this.ripe_rate_map = new HashMap<Material, Double[]>();
//		this.current_season = get_current_season();
		this.current_season = SeasonType.SPRING;
		
		this.force_next_season = false;

		// config test
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml")
				.exists()) {
			getLogger().info("config.yml doesn't exist. creating...");
			this.saveDefaultConfig();
		}

		List<String> list = this.getConfig().getStringList("ripe_rate");
		for (String str : list) {
			String[] array = str.split(",");
			Material crops = Material.valueOf(array[0]);
			double spring_rate = (double) Integer.parseInt(array[1]) / 100.0;
			double summer_rate = (double) Integer.parseInt(array[2]) / 100.0;
			double autumn_rate = (double) Integer.parseInt(array[3]) / 100.0;
			double winter_rate = (double) Integer.parseInt(array[4]) / 100.0;
			Double[] rate = { spring_rate, summer_rate, autumn_rate,
					winter_rate };

			this.ripe_rate_map.put(crops, rate);
		}

		// register listener
		this.getServer().getPluginManager()
				.registerEvents(new GrowEvent(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new PlayerJoin(this), this);
		this.getServer().getPluginManager()
				.registerEvents(new BarrenDirt(this), this);

		// run season check task
		new SeasonChanger(this).runTaskTimer(this, 10L, 50L);

		// register command
		getCommand("sf").setExecutor(new BasicCommand(this));
		
	}

	@Override
	public void onDisable() {

	}

	public String get_prefix() {
		return this.prefix;
	}

	public boolean get_force_next_season() {
		return this.force_next_season;
	}

	public void set_force_next_season(boolean next) {
		this.force_next_season = next;
	}

	public SeasonType get_current_season() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

		return SeasonType.values()[(day / 3) % SeasonType.values().length];
//		return this.current_season;
	}

	public void set_current_season(SeasonType season) {
		this.current_season = season;
	}

	public Double get_ripe_rate(Material material, SeasonType season) {
		return this.ripe_rate_map.get(material)[season.get_index()];
	}

	public boolean get_isContain_ripe_rate(Material material) {
		return this.ripe_rate_map.containsKey(material);
	}

	public void set_dirt_from_soil(Block block) {

		// is there dirt at beneath the block
		Block soil = block.getWorld().getBlockAt(
				block.getLocation().add(0, -1, 0));
		if (soil.getType().equals(Material.SOIL)) {
			soil.setType(Material.DIRT);
		}

	}

	public void display_season(boolean broadcast, Player player,
			SeasonType season) {

		Title change_season = new Title(season.get_inJapanese(), "< "
				+ season.toString() + " >");
		change_season.setTitleColor(season.get_season_color());
		change_season.setSubtitleColor(ChatColor.AQUA);

		if (broadcast) {
			change_season.broadcast();
			// season changing sound
			for (Player p : this.getServer().getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.AMBIENCE_CAVE, 1.5F, 0.8F);
			}

		}
		// to player
		else {

			if (!(player == null)) {

				change_season.send(player);
				player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE,
						1.5F, 0.8F);
			}
			else{
				this.getServer().broadcastMessage("PLAYER_NULL");
			}

		}

	}
}
