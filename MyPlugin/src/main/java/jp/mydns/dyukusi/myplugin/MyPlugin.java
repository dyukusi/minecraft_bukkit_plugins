package jp.mydns.dyukusi.myplugin;

import jp.mydns.dyukusi.myplugin.listener.PlayerEffect;
import jp.mydns.dyukusi.myplugin.mycommand.MyCommand;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@MYPLUGIN");

		this.getServer().getPluginManager().registerEvents(new PlayerEffect(this), this);
		// register command
		this.getCommand("mp").setExecutor(new MyCommand(this));

	}

	@Override
	public void onDisable() {

	}

}
