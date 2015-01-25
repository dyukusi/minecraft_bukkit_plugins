package jp.mydns.dyukusi.myplugin.achievement;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import me.teej107.customachievement.CustomAchievement;

public class MyAchievement extends CustomAchievement {

	private HashMap<UUID, Integer> blockBreaks;

	public MyAchievement(String name, Material material) {
		super(name, material);

		setColor(ChatColor.RED);

		setDisplayUnachieved(true);

		getLore().add("IT'S over 10");

		blockBreaks = new HashMap<UUID, Integer>();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		

		// Check to see if the player has a place in the map.
		if (!blockBreaks.containsKey(uuid)) {
			blockBreaks.put(uuid, 0);
		}

		// Adding one to the number of blocks broken
		blockBreaks.put(uuid, blockBreaks.get(uuid) + 1);

		// Lets check to see if the player deserves the achievement!
		if (blockBreaks.get(uuid) > 10) {
			// Award the achievement. The method already does checks to see if
			// the player already has it.
			giveAchievement(player);
		}
	}

}
