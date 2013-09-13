package com.github.hippoom.ramblings.airswitch.command.ticket;

public class AirTicketItemsCreatedEvent {
	private final Long ticketId;
	private final Long reservationId;
	private final double subtotal;

	public AirTicketItemsCreatedEvent(Long ticketId, Long reservationId,
			double subtotal) {
		this.ticketId = ticketId;
		this.reservationId = reservationId;
		this.subtotal = subtotal;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public double getSubtotal() {
		return subtotal;
	}
}
