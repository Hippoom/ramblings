package com.github.hippoom.ramblings.gordering.events;

import lombok.Getter;

@Getter
public class OrderPlacedEvent {
	private final String trackingId;
	private final String memberId;
	private final String status;

	public OrderPlacedEvent(String trackingId, String memberId, String status) {
		this.trackingId = trackingId;
		this.memberId = memberId;
		this.status = status;
	}
}
