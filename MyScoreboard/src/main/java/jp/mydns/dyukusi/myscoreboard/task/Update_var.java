package jp.mydns.dyukusi.myscoreboard.task;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myscoreboard.MyScoreboard;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Update_var extends BukkitRunnable {
	MyScoreboard plugin;
	Player player;
	Scoreboard board;
	Objective objective;
	Economy economy;
	CraftLevel craftlevel;

	public Update_var(MyScoreboard ms, Player p, Scoreboard b, Objective obj, Economy eco, CraftLevel cl) {
		this.plugin = ms;
		this.player = p;
		this.board = b;
		this.objective = obj;
		this.economy = eco;
		this.craftlevel = cl;
	}

	public void run() {

		if (objective != null) {

			objective.getScore(plugin.get_craftlevel_board()).setScore(
					craftlevel.get_player_crafting_level_info(player).get_level());

			objective.getScore(plugin.get_exp_board()).setScore(
					craftlevel.get_player_crafting_level_info(player).get_exp());
			objective.getScore(plugin.get_dollars_board()).setScore((int) economy.getBalance((OfflinePlayer) player));

			player.setScoreboard(board);
		}

	}
}
