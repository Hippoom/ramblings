package com.github.hippoom.ramblings.ordercrqs.command;

import java.math.BigDecimal;

import lombok.Getter;

public class PlaceOrderCommand {
	@Getter
	private final String trackingId;
	@Getter
	private final String bookingContact;
	@Getter
	private final BigDecimal totalAmount;

	public PlaceOrderCommand(String trackingId, String bookingContact,
			BigDecimal totalAmount) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
		this.totalAmount = totalAmount;
	}
}
