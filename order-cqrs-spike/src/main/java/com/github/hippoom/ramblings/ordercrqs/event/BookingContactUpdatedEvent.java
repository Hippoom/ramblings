package com.github.hippoom.ramblings.ordercrqs.event;

public class BookingContactUpdatedEvent {
	private final String trackingId;
	private final String bookingContact;

	public BookingContactUpdatedEvent(String trackingId, String bookingContact) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
	}
}
