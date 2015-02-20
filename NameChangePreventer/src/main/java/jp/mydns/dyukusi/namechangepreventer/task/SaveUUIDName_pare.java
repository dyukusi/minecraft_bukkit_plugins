package jp.mydns.dyukusi.namechangepreventer.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.scheduler.BukkitRunnable;

import jp.mydns.dyukusi.namechangepreventer.NameChangePreventer;

public class SaveUUIDName_pare extends BukkitRunnable {
	NameChangePreventer plugin;
	String path;
	HashMap<UUID, String> map;

	public SaveUUIDName_pare(NameChangePreventer p, String path,
			HashMap<UUID, String> map) {
		this.plugin = p;
		this.path = path;
		this.map = map;
	}

	@Override
	public void run() {
		plugin.getLogger().info("Saving UUID Name matching data...");

		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<UUID, String> ent : map.entrySet()) {
			String uuid = ent.getKey().toString();
			String name = ent.getValue();

			pw.println(name + "," + uuid);
		}

		pw.close();

		plugin.getLogger().info("Saving UUID Name matching data COMPLETED!!");
	}

}
