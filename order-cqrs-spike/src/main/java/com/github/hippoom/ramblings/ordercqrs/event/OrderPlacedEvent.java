package com.github.hippoom.ramblings.ordercqrs.event;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

@Getter
public class OrderPlacedEvent {

	private final String trackingId;

	private final Date whenPlaced;

	private final BigDecimal totalAmount;

	private final BigDecimal totalPaid;

	private final String balanceStatus;

	public OrderPlacedEvent(String trackingId, Date whenPlaced,
			BigDecimal totalAmount, BigDecimal totalPaid, String balanceStatus) {
		this.trackingId = trackingId;
		this.whenPlaced = whenPlaced;
		this.totalAmount = totalAmount;
		this.totalPaid = totalPaid;
		this.balanceStatus = balanceStatus;
	}

}
