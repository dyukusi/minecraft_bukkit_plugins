package jp.mydns.dyukusi.offlinedepositor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

import jp.mydns.dyukusi.offlinedepositor.command.Deposit;
import jp.mydns.dyukusi.offlinedepositor.listener.PlayerLogin;
import jp.mydns.dyukusi.offlinedepositor.value.DepositInformation;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class OfflineDepositor extends JavaPlugin implements Listener {
	String prefix = ChatColor.BLUE + "[OfflineDepositor]";
	String serialize_path;
	HashMap<String, LinkedList<DepositInformation>> depositor;
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

		this.getServer().getPluginManager().registerEvents(this, this);

		// シリアライズファイル読み込み
		serialize_path = this.getDataFolder().getAbsolutePath()
				+ "/depositmap.bin";
		try {
			ObjectInputStream objinput = new ObjectInputStream(
					new FileInputStream(serialize_path));
			depositor = (HashMap<String, LinkedList<DepositInformation>>) objinput
					.readObject();
			objinput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (depositor == null) {
			depositor = new HashMap<String, LinkedList<DepositInformation>>();
		}

		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		getCommand("od").setExecutor(new Deposit(this, economy));

		this.getServer()
				.getPluginManager()
				.registerEvents(new PlayerLogin(this, depositor, economy), this);

	}

	@Override
	public void onDisable() {
		try {
			ObjectOutputStream objoutput = new ObjectOutputStream(
					new FileOutputStream(serialize_path));
			objoutput.writeObject(this.depositor);
			objoutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

}
