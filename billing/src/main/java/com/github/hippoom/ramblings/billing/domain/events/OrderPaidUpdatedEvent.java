package com.github.hippoom.ramblings.billing.domain.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderPaidUpdatedEvent {
	private final String trackingId;
	private final double total;

	public OrderPaidUpdatedEvent(String trackingId, double total) {
		this.trackingId = trackingId;
		this.total = total;
	}
}
