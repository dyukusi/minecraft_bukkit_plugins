package jp.mydns.dyukusi.myplugin;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myplugin.listener.PlayerEffect;
import jp.mydns.dyukusi.myplugin.mycommand.MyCommand;
import jp.mydns.dyukusi.myplugin.mycommand.SaySomething;
import jp.mydns.dyukusi.myplugin.task.VehicleRidingHunger;
import me.teej107.customachievement.CAPluginAPI;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
	CraftLevel craftlevel;
	Economy economy;

	@Override
	public void onEnable() {

		// import vault
		if (!setupEconomy()) {
			System.out.println("Cannot read vault object!");
			return;
		}

		if ((this.craftlevel = (CraftLevel) this.getServer().getPluginManager()
				.getPlugin("CraftLevel")) == null) {

		}

		this.getCommand("s").setExecutor(new SaySomething(this));

		getLogger().info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@MYPLUGIN");

		this.getServer()
				.getPluginManager()
				.registerEvents(new PlayerEffect(this, craftlevel, economy),
						this);
		// register command
		this.getCommand("mp").setExecutor(new MyCommand(this));

		
		//riding hunger
		for (Player player : this.getServer().getOnlinePlayers()) {
			new VehicleRidingHunger(this, player).runTaskTimerAsynchronously(this, 10, 100);
		}

	}

	@Override
	public void onDisable() {

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
