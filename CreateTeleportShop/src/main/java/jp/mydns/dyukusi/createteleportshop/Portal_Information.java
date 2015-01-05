package jp.mydns.dyukusi.createteleportshop;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Location;

public class Portal_Information implements Serializable {
	private static final long serialVersionUID = 1L;
	private UUID creater_uuid;
	private int charge;
	private String comment;
	private int frequency;

	public Portal_Information(UUID CREATER_UUID, int COST, String COMMENT) {
		this.creater_uuid = CREATER_UUID;
		this.charge = COST;
		this.comment = COMMENT;
		this.frequency = 0;
	}

	UUID get_creater_uuid() {
		return this.creater_uuid;
	}

	int get_charge() {
		return this.charge;
	}

	String get_comment() {
		return this.comment;
	}
	
	int get_frequency(){		
		return this.frequency;
	}

	void set_comment(String COMMENT) {
		this.comment = COMMENT;
	}

	void set_charge(int new_charge) {
		this.charge = new_charge;
	}
	
	void increment_frequency(){
		this.frequency++;
	}

}
