package com.github.hippoom.ramblings.airswitch;

import com.github.hippoom.ramblings.airswitch.AirTicket.Status;

public class AirTicketCreatedEvent {
	private final Long ticketId;
	private final Long reservationId;
	private final AirTicket.Status status;

	public AirTicketCreatedEvent(Long ticketId, Long reservationId,
			Status status) {
		this.ticketId = ticketId;
		this.reservationId = reservationId;
		this.status = status;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public AirTicket.Status getStatus() {
		return status;
	}
}
