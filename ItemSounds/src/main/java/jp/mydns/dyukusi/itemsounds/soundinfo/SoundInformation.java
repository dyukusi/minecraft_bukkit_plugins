package jp.mydns.dyukusi.itemsounds.soundinfo;

import org.bukkit.Sound;

public class SoundInformation {
	private Sound sound;
	private float pitch;
	private float volume;

	public SoundInformation(Sound s, Float v, Float p) {
		this.sound = s;
		this.volume = v.floatValue();
		this.pitch = p.floatValue();
	}

	public Sound get_sound() {
		return this.sound;
	}

	public float get_pitch() {
		return this.pitch;
	}

	public float get_volume() {
		return this.volume;
	}
}
