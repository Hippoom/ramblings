package com.github.hippoom.ramblings.airswitch.command.ticket;

import java.util.Map;

public class CreateAirTicketCommand {

	private final Long ticketId;
	private final Long reservationId;
	private final Map<Integer, Double> fares;

	public CreateAirTicketCommand(Long ticketId, Long reservationId,
			Map<Integer, Double> fares) {
		this.ticketId = ticketId;
		this.reservationId = reservationId;
		this.fares = fares;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public Map<Integer, Double> getFares() {
		return fares;
	}
}
