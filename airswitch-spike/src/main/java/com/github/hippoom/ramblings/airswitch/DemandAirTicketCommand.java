package com.github.hippoom.ramblings.airswitch;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DemandAirTicketCommand {
	@TargetAggregateIdentifier
	private final Long ticketId;
	private final String number;

	public DemandAirTicketCommand(Long ticketId, String number) {
		this.ticketId = ticketId;
		this.number = number;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public String getNumber() {
		return number;
	}
}
