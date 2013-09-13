package com.github.hippoom.ramblings.airswitch.command.reservation;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CalculateTotalOfReservationCommand {
	@TargetAggregateIdentifier
	private final Long reservationId;
	private final Long ticketId;
	private final double augend;

	public CalculateTotalOfReservationCommand(Long reservationId,
			Long ticketId, double augend) {
		this.reservationId = reservationId;
		this.ticketId = ticketId;
		this.augend = augend;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public double getAugend() {
		return augend;
	}

	public Long getTicketId() {
		return ticketId;
	}
}
