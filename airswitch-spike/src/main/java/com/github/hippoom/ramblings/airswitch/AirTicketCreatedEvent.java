package com.github.hippoom.ramblings.airswitch;

import com.github.hippoom.ramblings.airswitch.AirTicket.Status;

public class AirTicketCreatedEvent {
	private final Long ticketId;
	private final Long reservationId;
	private final AirTicket.Status status;
	private final double totalAmount;

	public AirTicketCreatedEvent(Long ticketId, Long reservationId,
			Status status, double totalAmount) {
		this.ticketId = ticketId;
		this.reservationId = reservationId;
		this.status = status;
		this.totalAmount = totalAmount;
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

	public double getTotalAmount() {
		return totalAmount;
	}
}
