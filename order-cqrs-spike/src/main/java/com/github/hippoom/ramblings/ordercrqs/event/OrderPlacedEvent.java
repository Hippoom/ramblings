package com.github.hippoom.ramblings.ordercrqs.event;

import lombok.Getter;

public class OrderPlacedEvent {
	@Getter
	private final String trackingId;
	@Getter
	private final String bookingContact;

	public OrderPlacedEvent(String trackingId, String bookingContact) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
	}
}
