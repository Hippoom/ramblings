package com.github.hippoom.ramblings.gordering.commands;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Getter
public class UpdateOrderLineAmountCommand {
	@TargetAggregateIdentifier
	private final String trackingId;
	private final int seq;
	private double amount;

	public UpdateOrderLineAmountCommand(String trackingId, int seq,
			double amount) {
		this.trackingId = trackingId;
		this.seq = seq;
		this.amount = amount;
	}

}
