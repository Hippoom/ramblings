package com.github.hippoom.ramblings.ordercqrs.command;

import java.math.BigDecimal;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * <pre>
 * Modify the booking contact of given order due to 
 * 
 * 1.Incorrect information
 * 2.The current one is not available anymore
 * 
 * </pre>
 */
@Getter
public class MakePaymentCommand {

	@TargetAggregateIdentifier
	private final String trackingId;

	private final BigDecimal amount;

	public MakePaymentCommand(String trackingId, BigDecimal amount) {
		this.trackingId = trackingId;
		this.amount = amount;
	}
}
