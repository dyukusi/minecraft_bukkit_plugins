package jp.mydns.dyukusi.invasion.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import jp.mydns.dyukusi.invasion.Invasion;
import jp.mydns.dyukusi.invasion.custommonster_base.CustomEntityType;
import jp.mydns.dyukusi.invasion.invdata.InvMonsterData;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class load_all_invasion_task extends BukkitRunnable {

	Invasion plugin;
	InvasionData save_invation_data;

	public load_all_invasion_task(Invasion plugin, InvasionData invd) {
		this.plugin = plugin;
		this.save_invation_data = invd;
	}

	@Override
	public void run() {

		String path = plugin.get_inv_data_path();

		File inv_dir = new File(path);

		String inv_data_filenames[] = inv_dir.list();

		for (String filename : inv_data_filenames) {
			String inv_data_file_path = path + "/" + filename;
			String inv_name = filename.replaceAll(".yml", "");

			plugin.getServer().getLogger().info(inv_name + " detected");

			InvasionData load_invd = plugin.create_new_inv_data(inv_name);

			YamlReader reader = null;

			try {
				reader = new YamlReader(new FileReader(inv_data_file_path));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			Object object = null;
			try {
				object = reader.read();
			} catch (YamlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map map = (Map) object;
			Map map_invdata = (Map) map.get(inv_name);

			// get duration
			int duration = Integer.parseInt(map_invdata.get("duration")
					.toString());
			load_invd.set_duration(duration);

			// get spawncheck location
			List<Object> map_spawn_check = (List<Object>) map_invdata
					.get("spawn_check_location");
			for (Object ent : map_spawn_check) {
				String dest[] = ent.toString().split(",");
				double x = Double.parseDouble(dest[0]);
				double y = Double.parseDouble(dest[1]);
				double z = Double.parseDouble(dest[2]);

				load_invd.add_spawn_check_location(new Location(plugin
						.getServer().getWorld("world"), x, y, z));
			}

			// get destinations
			List<Object> map_destinations = (List<Object>) map_invdata
					.get("destination");
			for (Object ent : map_destinations) {
				String dest[] = ent.toString().split(",");
				double x = Double.parseDouble(dest[0]);
				double y = Double.parseDouble(dest[1]);
				double z = Double.parseDouble(dest[2]);

				load_invd.add_destination(new Location(plugin.getServer()
						.getWorld("world"), x, y, z));
			}

			// monsters
			Map map_spawn_monster = (Map) map_invdata.get("spawn_monster");
			for (Object ent : map_spawn_monster.keySet()) {
				String monster_name = ent.toString();
				Map map_monster_info = (Map) map_spawn_monster
						.get(monster_name);

				// monster type
				CustomEntityType monster_type = CustomEntityType
						.valueOf(map_monster_info.get("monster_type")
								.toString());

				// spawn location
				String[] spawn_location_split = map_monster_info
						.get("location").toString().split(",");
				Location spawn_location = new Location(plugin.getServer()
						.getWorld("world"),
						Double.parseDouble(spawn_location_split[0]),
						Double.parseDouble(spawn_location_split[1]),
						Double.parseDouble(spawn_location_split[2]));

				// health
				int health = Integer.parseInt(map_monster_info.get("health")
						.toString());

				// move speed
				double move_speed = Double.parseDouble(map_monster_info.get(
						"move_speed").toString());

				// jump level
				int jump_lv = Integer.parseInt(map_monster_info.get(
						"jump_level").toString());

				// add loaded monster
				load_invd.add_monster(new InvMonsterData(monster_type,
						spawn_location, health, move_speed, jump_lv));

			}

			plugin.getServer().broadcastMessage(
					ChatColor.GREEN + "Load " + inv_name
							+ " process has completed!");

		}

	}
}
