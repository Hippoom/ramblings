package com.github.hippoom.ramblings.airswitch.command.reservation;

import lombok.ToString;

import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservation.Status;

@ToString
public class AirReservationCreatedEvent {
	private final Long reservationId;
	private final AirReservation.Status status;

	public AirReservationCreatedEvent(Long reservationId, Status status) {
		this.reservationId = reservationId;
		this.status = status;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public AirReservation.Status getStatus() {
		return status;
	}
}
