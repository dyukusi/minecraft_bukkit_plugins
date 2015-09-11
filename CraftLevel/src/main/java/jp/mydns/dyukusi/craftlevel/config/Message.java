package jp.mydns.dyukusi.craftlevel.config;

import org.bukkit.Bukkit;

import jp.mydns.dyukusi.craftlevel.CraftLevel;

public enum Message {

	Craft_Success("Craft_Success"), Craft_Failure("Craft_Failure"), Craft_Gain_Experience(
			"Craft_Gain_Experience"), Craft_Success_Rate("Craft_Success_Rate"), Craft_Levelup(
			"Craft_Levelup"), Error_Shift("Error_Shift");

	private String message;

	private Message(String msg) {
		this.message = msg;
	}

	public String get_message() {
		return default_var_convert(CraftLevel.get_message(this, true));
	}

	public String get_message(String craft_item_name) {
		String msg = CraftLevel.get_message(this, true);
		msg = default_var_convert(msg);

		msg = msg.replace("^c", craft_item_name);
		return msg;
	}

	public String default_var_convert(String msg) {
		return msg.replace("^n", "\n");
	}

	public String get_message(int gain_exp, int current_exp,
			int next_level_exp, int success_rate) {
		String msg = CraftLevel.get_message(this, true);
		msg = default_var_convert(msg);

		msg = msg.replace("^g", String.valueOf(gain_exp));
		msg = msg.replace("^c", String.valueOf(current_exp));
		msg = msg.replace("^l", String.valueOf(next_level_exp));
		msg = msg.replace("^s", this.get_message(success_rate, false));

		return msg;
	}

	public String get_message(int success_rate, boolean display_prefix) {
		String msg = CraftLevel.get_message(Craft_Success_Rate, display_prefix);
		msg = default_var_convert(msg);
		msg = msg.replace("^s", String.valueOf(success_rate));
		return msg;
	}

	public String get_message(String name, int before_level, int new_level) {
		String msg = CraftLevel.get_message(this, true);
		msg = default_var_convert(msg);

		msg = msg.replace("^p", String.valueOf(name));
		msg = msg.replace("^b", String.valueOf(before_level));
		msg = msg.replace("^a", String.valueOf(new_level));

		return msg;
	}

}
