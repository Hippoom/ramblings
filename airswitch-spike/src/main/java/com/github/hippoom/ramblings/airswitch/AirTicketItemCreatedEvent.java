package com.github.hippoom.ramblings.airswitch;

import lombok.ToString;

@ToString
public class AirTicketItemCreatedEvent {
	private final Long ticketId;
	private final int rph;
	private final double fare;

	public AirTicketItemCreatedEvent(Long ticketId, int rph, double fare) {
		this.ticketId = ticketId;
		this.rph = rph;
		this.fare = fare;
	}

	public int getRph() {
		return rph;
	}

	public double getFare() {
		return fare;
	}

	public Long getTicketId() {
		return ticketId;
	}
}
