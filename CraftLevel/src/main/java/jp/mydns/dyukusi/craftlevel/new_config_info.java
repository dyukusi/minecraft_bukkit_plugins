package jp.mydns.dyukusi.craftlevel;


public class new_config_info implements Comparable<new_config_info> {

	String material_name;
	String str;

	public new_config_info(String name, String config_str) {
		this.material_name = name;
		this.str = config_str;
	}

	public int compareTo(new_config_info o) {
		return this.material_name.compareTo(o.material_name);
	}

}
