package com.github.hippoom.ramblings.airswitch;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CancelAirTicketCommand {
	@TargetAggregateIdentifier
	private final Long ticketId;
	private final double penalty;

	public CancelAirTicketCommand(Long ticketId, double penalty) {
		this.ticketId = ticketId;
		this.penalty = penalty;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public double getPenalty() {
		return penalty;
	}

}
