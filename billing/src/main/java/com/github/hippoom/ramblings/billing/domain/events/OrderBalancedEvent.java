package com.github.hippoom.ramblings.billing.domain.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderBalancedEvent {
	private final String trackingId;

	public OrderBalancedEvent(String trackingId) {
		this.trackingId = trackingId;
	}
}
