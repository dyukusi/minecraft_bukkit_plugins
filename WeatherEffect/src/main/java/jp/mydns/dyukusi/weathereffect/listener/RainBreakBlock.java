package jp.mydns.dyukusi.weathereffect.listener;

import java.util.HashMap;
import java.util.Map.Entry;

import jp.mydns.dyukusi.weathereffect.WeatherEffect;
import jp.mydns.dyukusi.weathereffect.process.BreakBlocks;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RainBreakBlock implements Listener {
	WeatherEffect plugin;

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

			HashMap<Chunk, Boolean> chunk_list = new HashMap<Chunk, Boolean>();

			for (Chunk ch : world.getLoadedChunks()) {
				if (!chunk_list.containsKey(ch)) {
					chunk_list.put(ch, true);
				} else {
					plugin.getServer().broadcastMessage("Duplicate task!!!");
				}
			}

			// 一つ以上のチャンクが読み込まれている時
			if (chunk_list.size() > 0) {

				// 全チャンクに対して
				int i = 0;
				for (Entry<Chunk, Boolean> ent : chunk_list.entrySet()) {
					Chunk key = ent.getKey();

					// １0秒毎にランダムで消失
					new BreakBlocks(plugin, key).runTaskTimerAsynchronously(
							this.plugin, (long) i * 7, 100L);
					i++;
				}

			}
		}
		// 雨以外の天候になった時
		else {
			plugin.getServer().broadcastMessage("雨があがりました！");
		}

	}

	// 雨が降っている時に、ジャック・オ・ランタン,松明を置いたら
	@EventHandler
	public void PlaceBlockInRain(BlockPlaceEvent event) {

		// 雨が降っている時
		if (event.getBlock().getWorld().hasStorm()) {
			Block block = event.getBlock();
			Material block_type = block.getType();

			PlaceBlock: if (block_type.equals(Material.JACK_O_LANTERN)
					|| block_type.equals(Material.TORCH)) {
				World world = event.getPlayer().getWorld();
				int max_height = world.getMaxHeight();
				int x = block.getX();
				int z = block.getZ();

				for (int y = block.getY() + 1; y < max_height - 1; y++) {
					Block roof = world.getBlockAt(x, y, z);
					Material roof_type = roof.getType();

					// 屋根があるなら問題無し
					if (!roof_type.equals(Material.AIR)) {
						break PlaceBlock;
					}
				}

				// 屋根が無いなら消失させる
				// ジャック・オ・ランタン
				if (block_type.equals(Material.JACK_O_LANTERN))
					block.setType(Material.PUMPKIN);
				// 松明
				else
					block.breakNaturally();

			}

		}
	}

}
