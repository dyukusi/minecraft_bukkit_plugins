package jp.mydns.dyukusi.offlinedepositor.value;

import java.io.Serializable;
import java.util.Calendar;

public class DepositInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String from, to;
	private double amount;
	private String reason;
	private Integer time[];

	public DepositInformation(String FROM, String TO, double AMOUNT,
			String REASON) {
		this.from = FROM;
		this.to = TO;
		this.amount = AMOUNT;
		this.reason = REASON;

		Calendar cl = Calendar.getInstance();
		this.time = new Integer[] { cl.get(Calendar.YEAR),
				cl.get(Calendar.MONTH), cl.get(Calendar.DATE),
				cl.get(Calendar.AM_PM), cl.get(Calendar.HOUR),
				cl.get(Calendar.MINUTE) };

	}

	public String get_from() {		
		return this.from;
	}

	public String get_to() {
		return this.to;
	}

	public double get_amount() {
		return this.amount;
	}

	public String get_reason() {
		return this.reason;
	}

	public Integer[] get_time() {
		return this.time;
	}

	public String get_time_str() {
		return time[0] + "-" + time[1] + "-" + time[2] + "-"
				+ (time[3] == 0 ? "AM" : "PM") + String.format("%1$02d",time[4]) + ":" + String.format("%1$02d",time[5]);
	}
}
