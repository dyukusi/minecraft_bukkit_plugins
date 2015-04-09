package jp.mydns.dyukusi.myachievements;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myachievements.achievements.Adventurer;
import jp.mydns.dyukusi.myachievements.achievements.AwakeToMine;
import jp.mydns.dyukusi.myachievements.achievements.BeginningOfYourStory;
import jp.mydns.dyukusi.myachievements.achievements.BountyHunter;
import jp.mydns.dyukusi.myachievements.achievements.ChainMania;
import jp.mydns.dyukusi.myachievements.achievements.CraftMaster;
import jp.mydns.dyukusi.myachievements.achievements.Departure;
import jp.mydns.dyukusi.myachievements.achievements.Destroyer;
import jp.mydns.dyukusi.myachievements.achievements.DiamondMania;
import jp.mydns.dyukusi.myachievements.achievements.EliteCrafter;
import jp.mydns.dyukusi.myachievements.achievements.EliteMiner;
import jp.mydns.dyukusi.myachievements.achievements.Excursion;
import jp.mydns.dyukusi.myachievements.achievements.ForThePeace;
import jp.mydns.dyukusi.myachievements.achievements.FullFledged;
import jp.mydns.dyukusi.myachievements.achievements.GiveMeMoreMoney;
import jp.mydns.dyukusi.myachievements.achievements.GoldMania;
import jp.mydns.dyukusi.myachievements.achievements.Hell;
import jp.mydns.dyukusi.myachievements.achievements.Hero;
import jp.mydns.dyukusi.myachievements.achievements.IronMania;
import jp.mydns.dyukusi.myachievements.achievements.Landowner;
import jp.mydns.dyukusi.myachievements.achievements.LeatherMania;
import jp.mydns.dyukusi.myachievements.achievements.LimitBreak;
import jp.mydns.dyukusi.myachievements.achievements.MineMaster;
import jp.mydns.dyukusi.myachievements.achievements.MoneyIsHeavierThanLife;
import jp.mydns.dyukusi.myachievements.achievements.Neighborhood;
import jp.mydns.dyukusi.myachievements.achievements.NoobCrafter;
import jp.mydns.dyukusi.myachievements.achievements.PhantomFish;
import jp.mydns.dyukusi.myachievements.achievements.PleasureOfThePoor;
import jp.mydns.dyukusi.myachievements.achievements.Pro;
import jp.mydns.dyukusi.myachievements.achievements.ProofOfBrave;
import jp.mydns.dyukusi.myachievements.achievements.Pugilist;
import jp.mydns.dyukusi.myachievements.achievements.Recruit;
import jp.mydns.dyukusi.myachievements.achievements.RoundTheWorld;
import jp.mydns.dyukusi.myachievements.achievements.Scrooge;
import jp.mydns.dyukusi.myachievements.achievements.SeekForTreasures;
import jp.mydns.dyukusi.myachievements.achievements.Sergeant;
import jp.mydns.dyukusi.myachievements.achievements.StoneCrafter;
import jp.mydns.dyukusi.myachievements.achievements.TouristAttraction;
import jp.mydns.dyukusi.myachievements.achievements.Training;
import jp.mydns.dyukusi.myachievements.achievements.Traveller;
import jp.mydns.dyukusi.myachievements.achievements.UnderTheSea;
import jp.mydns.dyukusi.myachievements.achievements.UnsatisfiedYet;
import jp.mydns.dyukusi.myachievements.achievements.WealthyMerchant;
import jp.mydns.dyukusi.myachievements.achievements.WoodCrafter;
import jp.mydns.dyukusi.myachievements.command.BasicCommands;
import jp.mydns.dyukusi.myachievements.listener.Achievements_listener;
import jp.mydns.dyukusi.myachievements.mystat.MyStat;
import jp.mydns.dyukusi.myachievements.task.BiomeAchievementCheck;
import jp.mydns.dyukusi.myachievements.task.CheckAchieveTask;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;
import me.teej107.customachievement.CAPluginAPI;
import me.teej107.customachievement.CustomAchievement;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.wolvencraft.yasp.StatisticsAPI;
import com.wolvencraft.yasp.session.OnlineSession;

public class MyAchievements extends JavaPlugin {

	LinkedList<AchieveInterface> achievement_list = new LinkedList<AchieveInterface>();
	Economy economy;
	CraftLevel craftlevel;
	OfflineDepositor depositor;

	MyStat mystat;

	@Override
	public void onEnable() {

		// read achievement save files
		this.mystat = new MyStat(this);
		try {
			mystat.read_all_files();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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
		achievement_list.add(new Training(depositor));
		achievement_list
				.add(new Departure(depositor, achievement_list.getLast()));
		achievement_list
		.add(new ForThePeace(depositor, achievement_list.getLast()));
		achievement_list
		.add(new FullFledged(depositor, achievement_list.getLast()));
		achievement_list.add(new Hell(depositor, achievement_list.getLast()));
		achievement_list.add(new ProofOfBrave(depositor, achievement_list.getLast()));		
		achievement_list.add(new BountyHunter(this, depositor,
				achievement_list.getLast()));
		achievement_list.add(new Hero(depositor, achievement_list.getLast()));

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

		// equip armors achievements
		achievement_list.add(new LeatherMania(depositor));
		achievement_list.add(new GoldMania(depositor));
		achievement_list.add(new ChainMania(depositor));
		achievement_list.add(new IronMania(depositor));
		achievement_list.add(new DiamondMania(depositor));

		// visit biome achievements
		achievement_list.add(new Neighborhood(depositor));
		achievement_list.add(new Excursion(depositor, achievement_list
				.getLast()));
		achievement_list.add(new Traveller(depositor, achievement_list
				.getLast()));
		achievement_list.add(new Adventurer(depositor, achievement_list
				.getLast()));
		achievement_list.add(new RoundTheWorld(depositor, achievement_list
				.getLast()));

		// play time achievements
		achievement_list.add(new Recruit(depositor));
		achievement_list
				.add(new Sergeant(depositor, achievement_list.getLast()));
		achievement_list.add(new Pro(depositor, achievement_list.getLast()));

		// others
		achievement_list.add(new Landowner(depositor));
		achievement_list.add(new LimitBreak(depositor));
		achievement_list.add(new PhantomFish(depositor));
		achievement_list.add(new WealthyMerchant(depositor, mystat));
		achievement_list.add(new Pugilist(depositor, mystat));
		achievement_list.add(new UnderTheSea(depositor));
		achievement_list.add(new TouristAttraction(depositor, mystat));

		CAPluginAPI ca = CAPluginAPI.getInstance();

		// register all achievements
		for (AchieveInterface ach : achievement_list) {
			ca.registerAchievement((CustomAchievement) ach, this);
		}

		BasicCommands bc = new BasicCommands(this, achievement_list);

		// register commands
		this.getCommand("ac").setExecutor(bc);

		// register listener
		this.getServer().getPluginManager().registerEvents(bc, this);
		this.getServer().getPluginManager()
				.registerEvents(new Achievements_listener(this, mystat,achievement_list), this);

		// check task of all player achievements
		int i = 0;
		for (Player player : this.getServer().getOnlinePlayers()) {
			OnlineSession session = StatisticsAPI.getSession(player);
			new CheckAchieveTask(this, player, session)
					.runTaskTimerAsynchronously(this, i++, 80);

			// not registered yet
			if (!mystat.get_biome_map().containsKey(player.getName())) {
				mystat.get_biome_map().put(player.getName(),
						new HashMap<String, Boolean>());
			}

			new BiomeAchievementCheck(this, player, mystat.get_biome_map().get(
					player.getName()), mystat).runTaskTimer(this, i++, 40);

		}

	}

	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelAllTasks();
		mystat.save_all_files();
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
