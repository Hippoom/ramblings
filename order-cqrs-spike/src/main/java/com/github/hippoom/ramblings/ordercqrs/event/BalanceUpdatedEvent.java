package com.github.hippoom.ramblings.ordercqrs.event;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public abstract class BalanceUpdatedEvent {

	private final String trackingId;
	private final BigDecimal totalAmount;
	private final BigDecimal paid;
	private final String balanceStatus;

	public BalanceUpdatedEvent(String trackingId, BigDecimal totalAmount,
			BigDecimal paid, String balanceStatus) {
		this.trackingId = trackingId;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.balanceStatus = balanceStatus;
	}

}