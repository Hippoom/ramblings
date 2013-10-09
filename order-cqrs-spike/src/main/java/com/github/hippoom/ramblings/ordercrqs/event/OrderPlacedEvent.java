package com.github.hippoom.ramblings.ordercrqs.event;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

public class OrderPlacedEvent {
	@Getter
	private final String trackingId;
	@Getter
	private final Date whenPlaced;
	@Getter
	private final BigDecimal totalAmount;

	public OrderPlacedEvent(String trackingId, Date whenPlaced,
			BigDecimal totalAmount) {
		this.trackingId = trackingId;
		this.whenPlaced = whenPlaced;
		this.totalAmount = totalAmount;
	}

}
