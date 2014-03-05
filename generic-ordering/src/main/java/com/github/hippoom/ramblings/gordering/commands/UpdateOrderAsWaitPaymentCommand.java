package com.github.hippoom.ramblings.gordering.commands;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@Getter
public class UpdateOrderAsWaitPaymentCommand {
	@TargetAggregateIdentifier
	private final String trackingId;

	public UpdateOrderAsWaitPaymentCommand(String trackingId) {
		this.trackingId = trackingId;
	}
}
