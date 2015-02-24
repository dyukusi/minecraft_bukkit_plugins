package jp.mydns.dyukusi.myachievements;

import me.teej107.customachievement.CustomAchievement;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.wolvencraft.yasp.session.OnlineSession;

public abstract class AchieveInterface extends CustomAchievement {
	public AchieveInterface(String name, Material material) {
		super(name, material);
	}

	public abstract boolean isAchieved(Player player, OnlineSession session);

	public abstract void getReward(Player player);
	
	public abstract int getInvIndex();
	
	public abstract boolean isDisplayInfo(Player player);
}
