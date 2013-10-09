package com.github.hippoom.ramblings.ordercqrs.command;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

public class PlaceOrderCommand {
	@Getter
	private final String trackingId;
	@Getter
	private final Date whenPlaced;
	@Getter
	private final String bookingContact;
	@Getter
	private final BigDecimal totalAmount;

	public PlaceOrderCommand(String trackingId, Date whenPlaced,
			String bookingContact, BigDecimal totalAmount) {
		this.trackingId = trackingId;
		this.whenPlaced = whenPlaced;
		this.bookingContact = bookingContact;
		this.totalAmount = totalAmount;
	}

}
