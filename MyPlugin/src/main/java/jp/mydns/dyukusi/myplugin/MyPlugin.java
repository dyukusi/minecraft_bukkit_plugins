package jp.mydns.dyukusi.myplugin;

import jp.mydns.dyukusi.craftlevel.CraftLevel;
import jp.mydns.dyukusi.myplugin.achievement.MyAchievement;
import jp.mydns.dyukusi.myplugin.listener.PlayerEffect;
import jp.mydns.dyukusi.myplugin.mycommand.MyCommand;
import me.teej107.customachievement.CAPluginAPI;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
	CraftLevel craftlevel;

	@Override
	public void onEnable() {

		if ((this.craftlevel = (CraftLevel) this.getServer().getPluginManager()
				.getPlugin("CraftLevel")) == null) {

		}

		getLogger().info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@MYPLUGIN");

		this.getServer().getPluginManager()
				.registerEvents(new PlayerEffect(this,craftlevel), this);
		// register command
		this.getCommand("mp").setExecutor(new MyCommand(this));

		CAPluginAPI ca = CAPluginAPI.getInstance();
		ca.registerAchievement(new MyAchievement("test_achieve",
				Material.GOLD_SWORD), this);
	}

	@Override
	public void onDisable() {

	}
	

}
