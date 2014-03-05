package com.github.hippoom.ramblings.gordering.events;

import lombok.Getter;

@Getter
public class OrderFullfilledEvent {
	private final String trackingId;
	private final String status;

	public OrderFullfilledEvent(String trackingId, String status) {
		this.trackingId = trackingId;
		this.status = status;
	}
}
