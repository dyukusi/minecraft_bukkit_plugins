package jp.mydns.dyukusi.myachievements;

import java.util.LinkedList;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.achievements.Adventurer;
import jp.mydns.dyukusi.myachievements.achievements.AwakeToMine;
import jp.mydns.dyukusi.myachievements.achievements.BeginningOfYourStory;
import jp.mydns.dyukusi.myachievements.achievements.Colonel;
import jp.mydns.dyukusi.myachievements.achievements.CraftMaster;
import jp.mydns.dyukusi.myachievements.achievements.Crawler;
import jp.mydns.dyukusi.myachievements.achievements.Destroyer;
import jp.mydns.dyukusi.myachievements.achievements.EliteCrafter;
import jp.mydns.dyukusi.myachievements.achievements.EliteMiner;
import jp.mydns.dyukusi.myachievements.achievements.GiveMeMoreMoney;
import jp.mydns.dyukusi.myachievements.achievements.Landowner;
import jp.mydns.dyukusi.myachievements.achievements.MineMaster;
import jp.mydns.dyukusi.myachievements.achievements.MoneyIsHeavierThanLife;
import jp.mydns.dyukusi.myachievements.achievements.NoobCrafter;
import jp.mydns.dyukusi.myachievements.achievements.PleasureOfThePoor;
import jp.mydns.dyukusi.myachievements.achievements.PrivateFirstClass;
import jp.mydns.dyukusi.myachievements.achievements.Pro;
import jp.mydns.dyukusi.myachievements.achievements.Recruit;
import jp.mydns.dyukusi.myachievements.achievements.RoundTheWorld;
import jp.mydns.dyukusi.myachievements.achievements.Runner;
import jp.mydns.dyukusi.myachievements.achievements.Scrooge;
import jp.mydns.dyukusi.myachievements.achievements.SeekForTreasures;
import jp.mydns.dyukusi.myachievements.achievements.Sergeant;
import jp.mydns.dyukusi.myachievements.achievements.StoneCrafter;
import jp.mydns.dyukusi.myachievements.achievements.Traveller;
import jp.mydns.dyukusi.myachievements.achievements.UnsatisfiedYet;
import jp.mydns.dyukusi.myachievements.achievements.WoodCrafter;
import jp.mydns.dyukusi.myachievements.command.BasicCommands;
import jp.mydns.dyukusi.myachievements.task.CheckAchieveTask;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import me.teej107.customachievement.CAPluginAPI;
import me.teej107.customachievement.CustomAchievement;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.wolvencraft.yasp.StatisticsAPI;
import com.wolvencraft.yasp.session.OnlineSession;

public class MyAchievements extends JavaPlugin implements Listener {

	LinkedList<AchieveInterface> achievement_list = new LinkedList<AchieveInterface>();
	Economy economy;
	CraftLevel craftlevel;
	OfflineDepositor depositor;
	long repeat_period = 100;

	@Override
	public void onEnable() {

		// check Statistics plugin is enabled or not
		// Plugin plugin = this.getServer().getPluginManager()
		// .getPlugin("Statistics");
		// if (plugin == null || !(plugin instanceof Statistics)) {
		// getLogger().info("Could not found Statistics plugin");
		// this.setEnabled(false); // Disable the plugin
		// return;
		// }

		// read CraftLevel
		if ((this.craftlevel = (CraftLevel) this.getServer().getPluginManager()
				.getPlugin("CraftLevel")) == null) {
			getLogger().info("Not found CraftLevel plugin in this server.");
		}

		// read OfflineDepositor
		if ((this.depositor = (OfflineDepositor) this.getServer()
				.getPluginManager().getPlugin("OfflineDepositor")) == null) {
			getLogger().info(
					"Not found OfflineDepositor plugin in this server.");
		}

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// main quests
		achievement_list.add(new BeginningOfYourStory(depositor));

		// craft achievements
		achievement_list.add(new NoobCrafter(depositor, craftlevel));
		achievement_list.add(new WoodCrafter(depositor, craftlevel,
				achievement_list.getLast()));
		achievement_list.add(new StoneCrafter(depositor, craftlevel,
				achievement_list.getLast()));
		achievement_list.add(new EliteCrafter(depositor, craftlevel,
				achievement_list.getLast()));
		achievement_list.add(new CraftMaster(depositor, craftlevel,
				achievement_list.getLast()));

		// save money achievements
		achievement_list.add(new PleasureOfThePoor(depositor, economy));
		achievement_list.add(new GiveMeMoreMoney(depositor, economy,
				achievement_list.getLast()));
		achievement_list.add(new MoneyIsHeavierThanLife(depositor, economy,
				achievement_list.getLast()));
		achievement_list.add(new UnsatisfiedYet(depositor, economy,
				achievement_list.getLast()));
		achievement_list.add(new Scrooge(depositor, economy, achievement_list
				.getLast()));

		// mine achievements
		achievement_list.add(new AwakeToMine(depositor));
		achievement_list.add(new SeekForTreasures(depositor, achievement_list
				.getLast()));
		achievement_list.add(new EliteMiner(depositor, achievement_list
				.getLast()));
		achievement_list.add(new MineMaster(depositor, achievement_list
				.getLast()));
		achievement_list.add(new Destroyer(depositor, achievement_list
				.getLast()));

		// walk distance achievements
		achievement_list.add(new Crawler(depositor));
		achievement_list.add(new Runner(depositor, achievement_list.getLast()));
		achievement_list.add(new Traveller(depositor, achievement_list
				.getLast()));
		achievement_list.add(new Adventurer(depositor, achievement_list
				.getLast()));
		achievement_list.add(new RoundTheWorld(depositor, achievement_list
				.getLast()));

		// play time achievement
		achievement_list.add(new Recruit(depositor));
		achievement_list.add(new PrivateFirstClass(depositor, achievement_list
				.getLast()));
		achievement_list
				.add(new Sergeant(depositor, achievement_list.getLast()));
		achievement_list
				.add(new Colonel(depositor, achievement_list.getLast()));
		achievement_list.add(new Pro(depositor, achievement_list.getLast()));

		// others
		achievement_list.add(new Landowner(depositor));

		CAPluginAPI ca = CAPluginAPI.getInstance();

		// register all achievements
		for (AchieveInterface ach : achievement_list) {
			ca.registerAchievement((CustomAchievement) ach, this);
		}

		// check task of all player achievements
		int i = 0;
		for (Player player : this.getServer().getOnlinePlayers()) {
			OnlineSession session = StatisticsAPI.getSession(player);
			new CheckAchieveTask(this, player, session)
					.runTaskTimerAsynchronously(this, i++, repeat_period);
		}

		BasicCommands bc = new BasicCommands(this, achievement_list);

		// register commands
		this.getCommand("ac").setExecutor(bc);

		// register listener
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(bc, this);

	}

	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelAllTasks();
	}

	@EventHandler
	void PlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		OnlineSession session = StatisticsAPI.getSession(player);
		new CheckAchieveTask(this, player, session).runTaskTimerAsynchronously(
				this, 60, repeat_period);
	}

	public LinkedList<AchieveInterface> get_achievements_list() {
		return this.achievement_list;
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

}
