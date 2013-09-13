package com.github.hippoom.ramblings.airswitch.command.reservation;

public class ItineraryUpdatedEvent {
	private final Long reservationId;
	private final String itinerary;

	public ItineraryUpdatedEvent(Long reservationId, String itinerary) {
		this.reservationId = reservationId;
		this.itinerary = itinerary;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public String getItinerary() {
		return itinerary;
	}
}
