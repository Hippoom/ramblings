package com.github.hippoom.ramblings.ordering.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderPlacedEvent {
	private final String trackingId;
	private final double total;

	public OrderPlacedEvent(String trackingId, double total) {
		this.trackingId = trackingId;
		this.total = total;
	}
}
