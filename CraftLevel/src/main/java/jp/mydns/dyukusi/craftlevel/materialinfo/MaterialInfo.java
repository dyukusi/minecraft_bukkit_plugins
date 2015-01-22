package jp.mydns.dyukusi.craftlevel.materialinfo;

import org.bukkit.Material;

public class MaterialInfo {
	private Material material;
	private int require_level;
	private int maximum_success_rate;
	private int experience_as_material;
	private int custom_experience;

	public MaterialInfo(Material Material, int RequireLevel,
			int MaximumSuccessRate, int ExperienceAsMaterial,
			int CustomExperience) {
		this.material = Material;
		this.require_level = RequireLevel;
		this.maximum_success_rate = MaximumSuccessRate;
		this.experience_as_material = ExperienceAsMaterial;
		this.custom_experience = CustomExperience;
	}

	public Material get_material() {
		return this.material;
	}

	public int get_require_level() {
		return this.require_level;
	}

	public int get_maximum_success_rate() {
		return this.maximum_success_rate;
	}

	public int get_experience_as_material() {
		return this.experience_as_material;
	}

	// off if -1
	public int get_custom_experience() {
		return this.custom_experience;
	}

}
