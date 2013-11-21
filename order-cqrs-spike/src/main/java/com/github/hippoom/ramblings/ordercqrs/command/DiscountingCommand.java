package com.github.hippoom.ramblings.ordercqrs.command;

import java.math.BigDecimal;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * <pre>
 * Decrease the payable amount of the given order due to
 * 
 * 1.Frequent customer
 * 2.On sales
 * 
 * </pre>
 */
@Getter
public class DiscountingCommand {
	@TargetAggregateIdentifier
	private final String trackingId;
	private final BigDecimal amount;

	/**
	 * 
	 * @param trackingId
	 *            as the identifier of given order
	 * @param amount
	 *            positive value as the amount of discounting
	 */
	public DiscountingCommand(String trackingId, BigDecimal amount) {
		this.trackingId = trackingId;
		this.amount = amount;
	}
}
