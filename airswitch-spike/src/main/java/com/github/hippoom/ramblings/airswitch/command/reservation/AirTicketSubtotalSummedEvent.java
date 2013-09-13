package com.github.hippoom.ramblings.airswitch.command.reservation;

public class AirTicketSubtotalSummedEvent {
	private final Long ticketId;
	private final Long reservationId;
	private final double subtotal;
	private final double currentTotal;

	public AirTicketSubtotalSummedEvent(Long reservationId, Long ticketId,
			double subtotal, double currentTotal) {
		this.ticketId = ticketId;
		this.reservationId = reservationId;
		this.subtotal = subtotal;
		this.currentTotal = currentTotal;
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

	public double getCurrentTotal() {
		return currentTotal;
	}
}
