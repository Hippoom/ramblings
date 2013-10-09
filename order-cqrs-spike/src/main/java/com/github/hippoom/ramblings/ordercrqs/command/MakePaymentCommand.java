package com.github.hippoom.ramblings.ordercrqs.command;

import java.math.BigDecimal;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class MakePaymentCommand {
	@Getter
	@TargetAggregateIdentifier
	private final String trackingId;
	@Getter
	private final BigDecimal amount;

	public MakePaymentCommand(String trackingId, BigDecimal amount) {
		this.trackingId = trackingId;
		this.amount = amount;
	}
}
