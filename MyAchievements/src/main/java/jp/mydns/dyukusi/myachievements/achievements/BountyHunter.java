package jp.mydns.dyukusi.myachievements.achievements;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.myachievements.AchieveInterface;
import jp.mydns.dyukusi.myachievements.MyAchievements;
import jp.mydns.dyukusi.offlinedepositor.OfflineDepositor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.wolvencraft.yasp.db.data.DataStore.DataStoreType;
import com.wolvencraft.yasp.session.OnlineSession;
import com.wolvencraft.yasp.util.VariableManager.PlayerVariable;

public class BountyHunter extends AchieveInterface {
	MyAchievements plugin;
	OfflineDepositor depositor;
	AchieveInterface before;

	public BountyHunter(MyAchievements myach, OfflineDepositor depositor,
			AchieveInterface before) {
		super("Bounty hunter", Material.FIRE);

		this.plugin = myach;
		this.depositor = depositor;
		this.before = before;

		setColor(ChatColor.YELLOW);

		List<String> lore_list = new ArrayList<String>();
		lore_list.add(ChatColor.WHITE + "ガストを１度の地獄探索で１０体狩る");
		lore_list.add(ChatColor.AQUA
				+ "< Hunt 10 Ghasts before return to the home world >");

		setLore(lore_list);
	}

	@Override
	public boolean isAchieved(Player player, OnlineSession session) {

		if (!player.getWorld().getEnvironment().equals(Environment.NETHER)) {
			player.setMetadata("ghast", new FixedMetadataValue(plugin, 0));
		} 
		
		if(player.hasMetadata("ghast")){
			if(player.getMetadata("ghast").get(0).asInt() >= 10){
				return true;
			}			
		}
		
		return false;
	}

	@Override
	public void getReward(Player player) {
		depositor.deposit("Bonus", player, 4000,
				" Hunt 10 Ghasts before return to the home world");
	}

	@Override
	public int getInvIndex() {
		return 7;
	}

	@Override
	public boolean isDisplayInfo(Player player) {
		if (before.hasAchievement(player)) {
			return true;
		}
		return false;
	}
}
