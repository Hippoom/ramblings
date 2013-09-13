package com.github.hippoom.ramblings.airswitch;

import com.github.hippoom.ramblings.airswitch.AirTicket.Status;

public class AirTicketDemandedEvent {
	private final Long ticketId;
	private final String number;
	private final AirTicket.Status status;

	public AirTicketDemandedEvent(Long ticketId, String number, Status status) {
		this.ticketId = ticketId;
		this.number = number;
		this.status = status;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public String getNumber() {
		return number;
	}

	public AirTicket.Status getStatus() {
		return status;
	}
}
