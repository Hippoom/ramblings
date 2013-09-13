package com.github.hippoom.ramblings.airswitch.command.reservation;

public class ItineraryUpdatedEvent {
	private final Long reservationId;
	private final String bookingContact;

	public ItineraryUpdatedEvent(Long reservationId, String bookingContact) {
		this.reservationId = reservationId;
		this.bookingContact = bookingContact;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public String getBookingContact() {
		return bookingContact;
	}
}
