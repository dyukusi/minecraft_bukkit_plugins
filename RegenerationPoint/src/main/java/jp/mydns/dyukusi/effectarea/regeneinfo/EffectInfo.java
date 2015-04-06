package jp.mydns.dyukusi.effectarea.regeneinfo;

import java.io.Serializable;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String effect;
	private int level;
	private int duration;

	public EffectInfo(String effect, int level, int duration) {
		this.effect = effect;
		this.level = level;
		this.duration = (duration*20);
	}

	public PotionEffectType get_potion_type() {
		return PotionEffectType.getByName(this.effect);
	}

	public int get_potion_level() {
		return this.level;
	}

	public PotionEffect get_PotionEffect() {
		return new PotionEffect(this.get_potion_type(), this.duration,
				this.level);
	}

}
