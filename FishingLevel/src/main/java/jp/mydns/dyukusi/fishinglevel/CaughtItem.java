package jp.mydns.dyukusi.fishinglevel;

import org.bukkit.inventory.ItemStack;

public class CaughtItem {
	ItemStack item;
	
	//0-100%
	int probability;

	public CaughtItem(ItemStack item, int probability) {
		this.item = item;
		this.probability = probability;
	}
	
	public ItemStack get_item(){
		return this.item;
	}
	
	public double get_probability(){
		return this.probability;
	}
	
}
