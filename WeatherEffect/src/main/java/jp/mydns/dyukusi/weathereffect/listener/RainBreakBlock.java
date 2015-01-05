package jp.mydns.dyukusi.weathereffect.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import jp.mydns.dyukusi.weathereffect.WeatherEffect;
import jp.mydns.dyukusi.weathereffect.process.BreakBlocks;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitTask;

public class RainBreakBlock implements Listener {
	boolean raining = false;
	WeatherEffect plugin;
	LinkedList<BukkitTask> task_list = new LinkedList<BukkitTask>();;

	// コンストラクタ
	public RainBreakBlock(WeatherEffect p) {
		this.plugin = p;

		// このリスナーの登録
		p.getServer().getPluginManager().registerEvents(this, p);
	}

	// テストログインイベント
	@EventHandler
	public void LoginEvent(PlayerLoginEvent login) {
		plugin.getServer().broadcastMessage(
				login.getPlayer().getDisplayName() + " WeatherEffect 起動てすと！");
	}

	// 雨が降り始めたら
	@EventHandler
	public void RainingEvent(WeatherChangeEvent event) {
		World world = event.getWorld();

		// 雨が降っている時
		if (event.toWeatherState()) {
			plugin.getServer().broadcastMessage("雨が降り始めました！");
			raining = true;
			Chunk[] chunks = world.getLoadedChunks();
			cancel_all_task(task_list);

			// 一つ以上のチャンクが読み込まれている時
			if (chunks.length > 0) {
				ArrayList<Chunk> chunk_list = new ArrayList<Chunk>(
						Arrays.asList(chunks));
				// シャッフル
				Collections.shuffle(chunk_list);

				// 全チャンクに対して
				for (int i = 0; i < chunk_list.size(); i++) {
					// １0秒毎にランダムで消失
					task_list.add(new BreakBlocks(plugin, world, chunk_list
							.get(i)).runTaskTimer(this.plugin, (long) i*3, 140L));					
				}
				plugin.getServer().broadcastMessage(task_list.size() + "個");

			}
		}
		// 雨以外の天候になった時
		else {
			plugin.getServer().broadcastMessage("雨があがりました！");
			raining = false;
			cancel_all_task(task_list);
		}

	}

	private void cancel_all_task(LinkedList<BukkitTask> task_list) {
		plugin.getServer().broadcastMessage(
				task_list.size() + "個のtaskをcancelします。");
		for (BukkitTask task : task_list) {
			task.cancel();
		}
	}

	// 雨が降っている時に、ジャック・オ・ランタンを置いたら
	// @EventHandler
	// public void PlaceBlockInRain(BlockPlaceEvent event) {
	//
	// // 雨が降っている時
	// if (raining) {
	// Block block = event.getBlock();
	// Material block_type = block.getType();
	//
	// PlaceBlock: if (block_type.equals(Material.JACK_O_LANTERN)) {
	// World world = event.getPlayer().getWorld();
	// int max_height = world.getMaxHeight();
	// int x = block.getX();
	// int z = block.getZ();
	//
	// for (int y = block.getY()+1; y < max_height - 1; y++) {
	// Block roof = world.getBlockAt(x, y, z);
	// Material roof_type = roof.getType();
	//
	// // 屋根があるなら問題無し
	// if (!roof_type.equals(Material.AIR)) {
	// break PlaceBlock;
	// }
	// }
	//
	// //屋根が無いなら消失させる
	// block.setType(Material.PUMPKIN);
	// }
	// }
	// }

}
