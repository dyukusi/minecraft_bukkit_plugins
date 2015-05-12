package jp.mydns.dyukusi.createteleportshop;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Location;

public class Portal_Information implements Serializable {
	private static final long serialVersionUID = 1L;
	private String creater_name;
	private int charge;
	private String comment;
	private int frequency;

	public Portal_Information(String CREATER_NAME, int COST, String COMMENT) {
		this.creater_name = CREATER_NAME;
		this.charge = COST;
		this.comment = COMMENT;
		this.frequency = 0;
	}

	String get_creater_name() {
		return this.creater_name;
	}

	int get_charge() {
		return this.charge;
	}

	String get_comment() {
		return this.comment;
	}

	int get_frequency() {
		return this.frequency;
	}

	void set_comment(String COMMENT) {
		this.comment = COMMENT;
	}

	void set_charge(int new_charge) {
		this.charge = new_charge;
	}

	void increment_frequency() {
		this.frequency++;
	}

}
