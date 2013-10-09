package com.github.hippoom.ramblings.ordercqrs.command;

import java.math.BigDecimal;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Getter
public class DiscountingCommand {
	@TargetAggregateIdentifier
	private final String trackingId;
	private final BigDecimal amount;

	public DiscountingCommand(String trackingId, BigDecimal amount) {
		this.trackingId = trackingId;
		this.amount = amount;
	}
}
