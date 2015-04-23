package jp.mydns.dyukusi.areamanager.listener;

import java.util.Map.Entry;

import jp.mydns.dyukusi.areamanager.AreaManager;
import jp.mydns.dyukusi.areamanager.areainfo.AreaInformation;
import jp.mydns.dyukusi.areamanager.task.AreaInfoProvidor;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlayerLoginOut implements Listener {

	AreaManager plugin;

	public PlayerLoginOut(AreaManager areaManager) {
		this.plugin = areaManager;
	}

	@EventHandler
	void PlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		new AreaInfoProvidor(plugin, player).runTaskTimer(plugin, 0, 13);

		ProtectedRegion c1 = plugin.get_wg()
				.getRegionManager(plugin.getServer().getWorld("world"))
				.getRegion("concourse1");
		ProtectedRegion c2 = plugin.get_wg()
				.getRegionManager(plugin.getServer().getWorld("world"))
				.getRegion("concourse2");
		ProtectedRegion c3 = plugin.get_wg()
				.getRegionManager(plugin.getServer().getWorld("world"))
				.getRegion("concourse3");
		ProtectedRegion c4 = plugin.get_wg()
				.getRegionManager(plugin.getServer().getWorld("world"))
				.getRegion("concourse4");
		
		c1.getMembers().addPlayer(player.getUniqueId());;
		c2.getMembers().addPlayer(player.getUniqueId());;
		c3.getMembers().addPlayer(player.getUniqueId());;
		c4.getMembers().addPlayer(player.getUniqueId());;

	}

	@EventHandler
	void PlayerLogout(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		for (Entry<String, AreaInformation> ent : plugin.get_areainfo_map().entrySet()) {
			AreaInformation info = ent.getValue();

			if (info.get_owner_name().equals(player.getName())) {
				int sec = (int) (System.currentTimeMillis() / 1000);
				int min = sec / 60;
				int hour = min / 60;
				info.set_last_played_time(hour);
			}
		}

	}

}
