package com.github.hippoom.ramblings.ordercqrs.event;

import java.math.BigDecimal;

public class OrderDiscountedEvent extends BalanceUpdatedEvent {

	public OrderDiscountedEvent(String trackingId, BigDecimal totalAmount,
			BigDecimal paid, String balanceStatus) {
		super(trackingId, totalAmount, paid, balanceStatus);
	}

}
