package com.github.hippoom.ramblings.ordercqrs.event;

import java.math.BigDecimal;

public class PaymentMadeEvent extends BalanceUpdatedEvent {
	public PaymentMadeEvent(String trackingId, BigDecimal totalAmount,
			BigDecimal paid, String balanceStatus) {
		super(trackingId, totalAmount, paid, balanceStatus);
	}

}
