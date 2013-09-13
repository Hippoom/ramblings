package com.github.hippoom.ramblings.airswitch.command.ticket;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AirTicketItem {

	private int rph;
	private double fare;

	public AirTicketItem(int rph, double fare) {
		this.rph = rph;
		this.fare = fare;
	}

	public int getRph() {
		return rph;
	}

	public double getFare() {
		return fare;
	}
}
