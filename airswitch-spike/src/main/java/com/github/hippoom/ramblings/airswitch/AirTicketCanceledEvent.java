package com.github.hippoom.ramblings.airswitch;

import com.github.hippoom.ramblings.airswitch.AirTicket.Status;

public class AirTicketCanceledEvent {
	private final Long ticketId;
	private final double penalty;
	private final AirTicket.Status status;

	public AirTicketCanceledEvent(Long ticketId, double penalty, Status status) {
		this.ticketId = ticketId;
		this.penalty = penalty;
		this.status = status;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public double getPenalty() {
		return penalty;
	}

	public AirTicket.Status getStatus() {
		return status;
	}
}
