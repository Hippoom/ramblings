package com.github.hippoom.ramblings.airswitch.command.reservation;

public class CreateAirReservationCommand {
	private final Long reservationId;
	private final String bookingContact;
	private final String itinerary;

	public CreateAirReservationCommand(Long reservationId,
			String bookingContact, String itinerary) {
		this.reservationId = reservationId;
		this.bookingContact = bookingContact;
		this.itinerary = itinerary;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public String getBookingContact() {
		return bookingContact;
	}

	public String getItinerary() {
		return itinerary;
	}
}
