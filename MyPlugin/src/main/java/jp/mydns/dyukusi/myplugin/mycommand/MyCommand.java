package jp.mydns.dyukusi.myplugin.mycommand;

import jp.mydns.dyukusi.myplugin.MyPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommand implements CommandExecutor {

	MyPlugin plugin;

	public MyCommand(MyPlugin myPlugin) {
		this.plugin = myPlugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (command.getName().equals("mp")) {

				// set positions
				if (args.length == 1) {
					
					if(args[0].equals("demo")){
					
						//城塞都市
						player.teleport(new Location(player.getWorld(), -4923, 65, 5315));
						
						return true;
					}
					else if(args[0].equals("kastel")){
						
						//kastel仮拠点
						player.teleport(new Location(player.getWorld(), -670, 69, 602));
						
						return true;
					}
					
				}
				
			}
			
		}
		
		
		return false;
	}

}
