package com.github.hippoom.ramblings.ordercrqs.event;

import java.math.BigDecimal;

import lombok.Getter;

public class BalanceRecalculatedEvent {
	private final String trackingId;
	@Getter
	private final BigDecimal totalAmount;
	@Getter
	private final BigDecimal paid;
	private final String balanceStatus;

	public BalanceRecalculatedEvent(String trackingId, BigDecimal totalAmount,
			BigDecimal paid, String balanceStatus) {
		this.trackingId = trackingId;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.balanceStatus = balanceStatus;
	}

}
