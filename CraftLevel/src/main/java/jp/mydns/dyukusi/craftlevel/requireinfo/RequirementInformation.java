package jp.mydns.dyukusi.craftlevel.requireinfo;

import java.io.Serializable;

import org.bukkit.Material;

public class RequirementInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Material material;
	private int require_level;
	private int maximum_success_rate;

	public RequirementInformation(Material MATERIAL, int REQUIRE_LEVEL, int MAXIMUM_SUCCESS_RATE) {
		this.material = MATERIAL;
		this.require_level = REQUIRE_LEVEL;
		this.maximum_success_rate = MAXIMUM_SUCCESS_RATE;
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

}
