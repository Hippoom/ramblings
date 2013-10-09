package com.github.hippoom.ramblings.ordercqrs.event;

import lombok.Getter;

public class OrderBalanceEvent {
	@Getter
	private final String trackingId;

	public OrderBalanceEvent(String trackingId) {
		this.trackingId = trackingId;
	}
}
