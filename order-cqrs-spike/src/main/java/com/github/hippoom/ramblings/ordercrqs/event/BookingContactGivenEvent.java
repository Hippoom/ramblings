package com.github.hippoom.ramblings.ordercrqs.event;

/**
 * 
 * the booking contact when the order is placed
 * 
 */
public class BookingContactGivenEvent extends BookingContactUpdatedEvent {

	public BookingContactGivenEvent(String trackingId, String bookingContact) {
		super(trackingId, bookingContact);
	}

}
