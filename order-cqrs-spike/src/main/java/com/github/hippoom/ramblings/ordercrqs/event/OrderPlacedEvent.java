package com.github.hippoom.ramblings.ordercrqs.event;

import java.math.BigDecimal;

import lombok.Getter;

public class OrderPlacedEvent {
	@Getter
	private final String trackingId;
	@Getter
	private final String bookingContact;
	@Getter
	private final BigDecimal totalAmount;

	public OrderPlacedEvent(String trackingId, String bookingContact,
			BigDecimal totalAmount) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
		this.totalAmount = totalAmount;
	}

}
