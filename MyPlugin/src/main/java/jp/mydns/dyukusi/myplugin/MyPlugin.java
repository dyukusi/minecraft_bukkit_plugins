package jp.mydns.dyukusi.myplugin;

import jp.mydns.dyukusi.myplugin.listener.PlayerEffect;

import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@MYPLUGIN");

		this.getServer().getPluginManager().registerEvents(new PlayerEffect(this), this);
	}

	@Override
	public void onDisable() {

	}

}
