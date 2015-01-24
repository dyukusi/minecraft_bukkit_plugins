package jp.mydns.dyukusi.craftlevel.materialinfo;

import java.util.ArrayList;
import java.util.List;

import jp.mydns.dyukusi.craftlevel.CraftLevel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class MaterialInfo {
	private Material material;
	private int require_level;
	private int maximum_success_rate;
	private int minimum_success_rate;
	private int experience_as_material;
	private int custom_experience;
	private int fixed_success_rate;

	public MaterialInfo(Material Material, int RequireLevel,
			int MaximumSuccessRate, int MinimumSuccessRate,
			int FixedSuccessRate, int ExperienceAsMaterial, int CustomExperience) {
		this.material = Material;
		this.require_level = RequireLevel;
		this.maximum_success_rate = MaximumSuccessRate;
		this.minimum_success_rate = MinimumSuccessRate;
		this.fixed_success_rate = FixedSuccessRate;
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

	public int get_minimum_success_rate() {
		return this.minimum_success_rate;
	}

	public int get_experience_as_material() {
		return this.experience_as_material;
	}

	// off if -1
	public int get_custom_experience() {
		return this.custom_experience;
	}

	public int get_fixed_success_rate() {
		return this.fixed_success_rate;
	}

	public double get_success_rate(int level) {
		int success_rate = minimum_success_rate;

		if (level >= require_level) {
			success_rate += CraftLevel.get_increase_rate()
					+ (CraftLevel.get_increase_rate() * (level - require_level));
		}
		// default maximum success rate 0-100 %
		if (success_rate > maximum_success_rate) {
			success_rate = maximum_success_rate;
		} else if (success_rate < minimum_success_rate) {
			success_rate = minimum_success_rate;
		}

		if (fixed_success_rate > 0) {
			success_rate = fixed_success_rate;
		}

		// 0 ~ 1.0
		return (double) success_rate / 100;
	}

	public int get_success_exp(Recipe recipe) {
		int exp = 0;
		List<Material> contents = new ArrayList<Material>();

		// ShapedRecipe
		if (recipe instanceof ShapedRecipe) {
			ShapedRecipe sr = (ShapedRecipe) recipe;
			for (ItemStack item : sr.getIngredientMap().values()) {
				if (item != null)
					contents.add(item.getType());
			}
		}
		// ShapelessRecipe
		else if (recipe instanceof ShapelessRecipe) {
			ShapelessRecipe sl = (ShapelessRecipe) recipe;
			for (ItemStack item : sl.getIngredientList()) {
				if (item != null)
					contents.add(item.getType());
			}
		}
		// undefined instance
		else {
			Bukkit.broadcastMessage(CraftLevel.get_prefix()
					+ " undefined recipe instance. please tell Dyukusi to fix this bug.");
			return 0;
		}

		for (Material material : contents) {
			MaterialInfo info = CraftLevel.get_material_info(material);
			if (info != null) {
				exp += info.get_experience_as_material();
			}
		}
		return exp;
	}

}
