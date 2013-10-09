package com.github.hippoom.ramblings.ordercqrs.event;

import lombok.Getter;

@Getter
public class BookingContactUpdatedEvent {

	private final String trackingId;
	private final String bookingContact;

	public BookingContactUpdatedEvent(String trackingId, String bookingContact) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
	}

}