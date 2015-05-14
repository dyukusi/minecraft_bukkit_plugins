package jp.mydns.dyukusi.fishinglevel.command;

import jp.mydns.dyukusi.fishinglevel.FishingLevel;
import jp.mydns.dyukusi.fishinglevel.playerdata.PlayerFishingLevelData;
import jp.mydns.dyukusi.fishinglevel.task.SavePlayerFishingData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class BaisicCommands implements CommandExecutor {

	FishingLevel plugin;

	public BaisicCommands(FishingLevel fishingLevel) {
		this.plugin = fishingLevel;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Need to executed by a player to do this command.");
			return false;
		}

		Player player = (Player) sender;
		PlayerFishingLevelData data = this.plugin.get_playerfishleveldata()
				.get(player.getName());

		if (command.getName().equals("fl")) {

			switch (args.length) {
			case 0:
				player.sendMessage(ChatColor.BLUE + "[FishingLevel]"
						+ ChatColor.WHITE + " Level: " + ChatColor.GOLD
						+ data.get_level());
				return true;
			case 1:
				if(args[0].equals("save")){
					
					new SavePlayerFishingData(plugin, plugin.get_playerfishingdata_path()).runTask(plugin);
				}
				return true;
				
			case 2:
				if(args[0].equals("tweet")){
					
					ConfigurationBuilder cb = new ConfigurationBuilder();
					cb.setDebugEnabled(true)
					  .setOAuthConsumerKey("0Y9QYxCTHlcCFGkDWPEY7XD6x")
					  .setOAuthConsumerSecret("11t4KY8hzUKMl9pCArSXHuvBXSSR8W0dF0wiWp1rBalW4lVdiI")
					  .setOAuthAccessToken("3194220091-8fNzLRlhzHLb49biPIGxnv2AERQ9z0Z1hAfwOur")
					  .setOAuthAccessTokenSecret("veBBUpMsuNmx1568PBIvwcfVX6e8zXHr5thPfpcmoj3yj");
					TwitterFactory tf = new TwitterFactory(cb.build());
					Twitter twitter = tf.getInstance();
					
					User user = null;
					try {
						user = twitter.verifyCredentials();
					} catch (TwitterException e) {
						e.printStackTrace();
					}
					
					player.sendMessage("Name : "+user.getName());
					player.sendMessage("DisplayName : "+user.getScreenName());
					
					//tweet
					try {
						twitter4j.Status status = twitter.updateStatus(args[1]);
						player.sendMessage("status : "+status.getCreatedAt().toString());	
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}				
				return true;
				
			case 4:
				if (args[0].equals("set")) {

					if (args[1].equals("level")) {

						PlayerFishingLevelData who = this.plugin
								.get_playerfishleveldata().get(args[2]);

						int set_level = Integer.parseInt(args[3]);

						who.set_level(set_level);
						player.sendMessage(ChatColor.BLUE + "Changed " + args[2]
								+ "'s fishing level to " + set_level);

					}

				}
				return true;

			default:
				break;
			}

		}

		return false;
	}

}
