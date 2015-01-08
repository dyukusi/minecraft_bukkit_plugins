package jp.mydns.dyukusi.myscoreboard;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myscoreboard.task.Update_var;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class MyScoreboard extends JavaPlugin implements Listener {
	CraftLevel craftlevel;
	Economy economy = null;
	private String dollar_board = "Dollars";
	private String craftlevel_board = "CraftLv";
	private String exp_board = "CraftExp";

	public String get_dollars_board() {
		return this.dollar_board;
	}

	public String get_craftlevel_board() {
		return this.craftlevel_board;
	}

	public String get_exp_board() {
		return this.exp_board;
	}

	@Override
	public void onEnable() {
		if ((this.craftlevel = (CraftLevel) this.getServer().getPluginManager().getPlugin("CraftLevel")) == null) {

		}
		this.getServer().getPluginManager().registerEvents(this, this);

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// for (Player player : getServer().getOnlinePlayers()) {
		// set_status_scoreboard(player);
		// }

	}

	@Override
	public void onDisable() {

	}

	@EventHandler
	void PlayerJoin(PlayerJoinEvent event) {
		// set_status_scoreboard(event.getPlayer());
		Player player = event.getPlayer();
		ScoreboardManager manager = this.getServer().getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective;
		if ((objective = board.getObjective("status")) == null) {
			objective = board.registerNewObjective("status", player.getName());
		}
		objective.setDisplayName(ChatColor.DARK_GREEN + "STATUS");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.getScore(craftlevel_board);
		objective.getScore(exp_board);
		objective.getScore(dollar_board);
		player.getPlayer().setScoreboard(board);
		new Update_var(this, event.getPlayer(), board, objective, economy, craftlevel).runTaskTimer(this, 10, 5);
	}

	// void set_status_scoreboard(Player player) {
	//
	// }

	boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
				net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			this.economy = economyProvider.getProvider();
		}
		return (economy != null);
	}
}
