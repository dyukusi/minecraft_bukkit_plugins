package jp.mydns.dyukusi.invasion;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jp.mydns.dyukusi.invasion.command.InvasionCommands;
import jp.mydns.dyukusi.invasion.custommonster_base.CustomEntityType;
import jp.mydns.dyukusi.invasion.custommonster_base.getPfield_registerEnt;
import jp.mydns.dyukusi.invasion.invdata.InvasionData;
import jp.mydns.dyukusi.invasion.listener.InvasionListeners;
import jp.mydns.dyukusi.invasion.task.save_invation_data_task;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Invasion extends JavaPlugin {

	private String inv_path = getDataFolder().getAbsolutePath();
	private String inv_data_path = inv_path + "/InvasionData";
	private Map<String, InvasionData> inv_data;

	@Override
	public void onEnable() {

		// confirm and create directory if required directory wasn't exist
		File dir = new File(inv_path);
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				this.getServer().getLogger()
						.info("Couldn't create Invation directory.");
				return;
			}
		}

		dir = new File(inv_path + "/InvasionData");
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				this.getServer().getLogger()
						.info("Couldn't create InvationData directory.");
				return;
			}
		}

		// register listener
		getServer().getPluginManager().registerEvents(
				new InvasionListeners(this), this);

		this.inv_data = new HashMap<String, InvasionData>();

		// register commands
		this.getCommand("inv")
				.setExecutor(new InvasionCommands(this, inv_data));

		// register custom monsters
		getPfield_registerEnt.registerEntities();

		// test case
		// this.inv_data.put("test", new InvasionData("test"));
		// InvasionData data = inv_data.get("test");
		// data.set_duration(10);
		// data.set_spawn_rate(1.0);
		// data.add_monster(CustomEntityType.NORMALSKELETON, 100, 6, 1.0, 3);
		// data.add_monster(CustomEntityType.NORMALZOMBIE, 95, 1, 2.0, 4);
		// data.add_monster(CustomEntityType.NORMALCREEPER,100, 2, 1.0, 5);

		// World world = this.getServer().getWorld("world");
		// data.add_destination(new Location(world, 1700, 6, 1263));
		// data.add_destination(new Location(world, 1966, 5, 1261));

		super.onEnable();
	}

	@Override
	public void onDisable() {

		// new save_invation_data_task(this, inv_data, this.inv_save_path)
		// .runTask(this);

		super.onDisable();
	}

	public InvasionData create_new_inv_data(String inv_name) {
		InvasionData new_invd = new InvasionData(inv_name);		
		this.inv_data.put(inv_name, new_invd);
		return new_invd;
	}

	public String get_inv_path() {
		return this.inv_path;
	}
	
	public String get_inv_data_path() {
		return this.inv_data_path;
	}
	

}
