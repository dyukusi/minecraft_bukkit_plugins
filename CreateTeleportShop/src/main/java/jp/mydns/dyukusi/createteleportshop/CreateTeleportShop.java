package jp.mydns.dyukusi.createteleportshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class CreateTeleportShop extends JavaPlugin {
	String serialize_path;
	private HashMap<String, Portal_Information> creater_map;
	Economy economy = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {

		// mcstats
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.RED + "Failed to submit the stats");
			// Failed to submit the stats
		}

		getCommand("cts").setExecutor(new command(this));

		// シリアライズファイル読み込み
		serialize_path = this.getDataFolder().getAbsolutePath()
				+ "/creatermap.bin";
		try {
			ObjectInputStream objinput = new ObjectInputStream(
					new FileInputStream(serialize_path));
			creater_map = (HashMap<String, Portal_Information>) objinput
					.readObject();
			objinput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (creater_map == null) {
			creater_map = new HashMap<String, Portal_Information>();
		}

		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		// for (java.util.Map.Entry<String, Portal_Information> ent :
		// this.creater_map
		// .entrySet()) {
		// System.out.println(ent.getKey() + " : "
		// + this.getServer().getPlayer(ent.getValue().creater_uuid).getName());
		// }

		this.getServer().getPluginManager()
				.registerEvents(new cts_listener(this, economy), this);
	}

	@Override
	public void onDisable() {
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(serialize_path));
			objoutput.writeObject(this.creater_map);
			objoutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void SaveCreatePortalData(String portal_name, Portal_Information pi) {
		this.creater_map.put(portal_name, pi);
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

	HashMap<String, Portal_Information> get_creater_map() {
		return this.creater_map;
	}

	int get_fare(Location loc) {
		int center_x = 1682;
		int center_z = 1256;

		int x = loc.getBlockX();
		int z = loc.getBlockZ();

		int diff_x = Math.abs(center_x - x);
		int diff_z = Math.abs(center_z - z);

		int distance = (int) Math.sqrt(Math.pow(diff_x, 2)
				+ Math.pow(diff_z, 2));

		//in city
		if (distance <= 255)
			return 0;
		else{
			distance -= 255;
		}
		
		if(distance < 0){
			distance = 0;
		}
		
		return distance/2;
	}

}
