package com.github.hippoom.ramblings.booking.events;

import lombok.Getter;

@Getter
public class ReservationSpecificationHandledEvent {
	private final String reservationId;
	private final String trackingId;
	private final int seq;
	private final double totalAmount;

	public ReservationSpecificationHandledEvent(String reservationId, String trackingId,
			int seq, double totalAmount) {
		this.reservationId = reservationId;
		this.trackingId = trackingId;
		this.seq = seq;
		this.totalAmount = totalAmount;
	}
}
