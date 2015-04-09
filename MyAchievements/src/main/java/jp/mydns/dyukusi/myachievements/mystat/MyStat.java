package jp.mydns.dyukusi.myachievements.mystat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import jp.mydns.dyukusi.craftlevel.new_config_info;
import jp.mydns.dyukusi.myachievements.MyAchievements;

public class MyStat {

	MyAchievements plugin;

	public MyStat(MyAchievements myach) {
		this.plugin = myach;
		plugin_path = plugin.getDataFolder().getAbsolutePath() + "/";
	}

	String plugin_path;
	String biome_file_name = "biome_achievement.dat";
	String kill_punch_file_name = "kill_punch_achievement.txt";
	String sell_total_file_name = "sell_total_achievement.txt";

	private HashMap<String, HashMap<String, Boolean>> biome_map;
	private HashMap<String, Integer> kill_by_punch_map;
	private HashMap<String, Integer> sell_total_map;

	public HashMap<String, HashMap<String, Boolean>> get_biome_map() {
		return this.biome_map;
	}

	public HashMap<String, Integer> get_kill_punch_map() {
		return this.kill_by_punch_map;
	}

	public HashMap<String, Integer> get_sell_total_map() {
		return this.sell_total_map;
	}

	public void read_all_files() throws FileNotFoundException {

		this.read_biome_ach_data(plugin_path + biome_file_name);
		this.read_kill_by_punch(plugin_path + kill_punch_file_name);
		this.read_sell_total(plugin_path + sell_total_file_name);

	}

	public void save_all_files() {
		this.save_biome_data(plugin_path + biome_file_name, biome_map);
		this.save_kill_by_punch_data(plugin_path + kill_punch_file_name,
				kill_by_punch_map);
		this.save_sell_total(plugin_path + sell_total_file_name, sell_total_map);

	}

	@SuppressWarnings("unchecked")
	private void read_biome_ach_data(String path) {

		if (new File(path).exists()) {

			// read visit biome serialize map data
			try {
				ObjectInputStream objinput = new ObjectInputStream(
						new FileInputStream(path));
				biome_map = (HashMap<String, HashMap<String, Boolean>>) objinput
						.readObject();
				objinput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		// could not read the save file
		else {
			biome_map = new HashMap<String, HashMap<String, Boolean>>();
		}

		if (biome_map == null)
			biome_map = new HashMap<String, HashMap<String, Boolean>>();

		plugin.getLogger().info("Read biome file completed!");
	}

	private void save_biome_data(String path,
			HashMap<String, HashMap<String, Boolean>> map) {
		// save biome data
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(path));
			objoutput.writeObject(map);
			objoutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void read_kill_by_punch(String path) throws FileNotFoundException {

		kill_by_punch_map = new HashMap<String, Integer>();

		if (new File(path).exists()) {

			Scanner sc = new Scanner(new File(path));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				String split[] = line.split(",");

				String player_name = split[0];
				int kill_num = Integer.parseInt(split[1]);

				kill_by_punch_map.put(player_name, kill_num);
			}
		}

		plugin.getLogger().info("Read kill punch file completed!");
	}

	private void save_kill_by_punch_data(String path,
			HashMap<String, Integer> map) {

		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<String, Integer> ent : map.entrySet()) {
			pw.println(ent.getKey() + "," + ent.getValue());
		}

		pw.close();
	}

	private void read_sell_total(String path) throws FileNotFoundException {

		sell_total_map = new HashMap<String, Integer>();

		if (new File(path).exists()) {

			Scanner sc = new Scanner(new File(path));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				String split[] = line.split(",");

				String player_name = split[0];
				int sell_total = Integer.parseInt(split[1]);

				sell_total_map.put(player_name, sell_total);
			}
		}

		plugin.getLogger().info("Read sell total file completed!");
	}

	private void save_sell_total(String path, HashMap<String, Integer> map) {
		File file = new File(path);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<String, Integer> ent : map.entrySet()) {
			pw.println(ent.getKey() + "," + ent.getValue());
		}

		pw.close();
	}

}
