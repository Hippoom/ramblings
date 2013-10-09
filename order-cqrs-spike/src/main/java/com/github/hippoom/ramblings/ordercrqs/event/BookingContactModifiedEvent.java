package com.github.hippoom.ramblings.ordercrqs.event;

/**
 * 
 * This event tells the original booking contact is incorrect and is modified
 * 
 */
public class BookingContactModifiedEvent extends BookingContactUpdatedEvent {
	public BookingContactModifiedEvent(String trackingId, String bookingContact) {
		super(trackingId, bookingContact);
	}
}
