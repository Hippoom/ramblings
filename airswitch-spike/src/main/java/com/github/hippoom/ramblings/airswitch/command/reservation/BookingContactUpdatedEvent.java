package com.github.hippoom.ramblings.airswitch.command.reservation;

public class BookingContactUpdatedEvent {
	private final Long reservationId;
	private final String bookingContact;

	public BookingContactUpdatedEvent(Long reservationId, String bookingContact) {
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
