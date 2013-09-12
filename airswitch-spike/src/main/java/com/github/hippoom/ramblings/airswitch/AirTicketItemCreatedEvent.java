package com.github.hippoom.ramblings.airswitch;

import lombok.ToString;

@ToString
public class AirTicketItemCreatedEvent {
	private final int rph;
	private final double fare;

	public AirTicketItemCreatedEvent(int rph, double fare) {
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
