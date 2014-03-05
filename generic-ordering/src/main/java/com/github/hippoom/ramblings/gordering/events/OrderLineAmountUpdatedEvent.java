package com.github.hippoom.ramblings.gordering.events;

import lombok.Getter;

@Getter
public class OrderLineAmountUpdatedEvent {
	private final String trackingId;
	private final int seq;
	private double amount;

	public OrderLineAmountUpdatedEvent(String trackingId, int seq, double amount) {
		this.trackingId = trackingId;
		this.seq = seq;
		this.amount = amount;
	}
}
