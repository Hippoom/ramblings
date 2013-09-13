package com.github.hippoom.ramblings.airswitch.command.reservation;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings("serial")
public class AirReservation extends AbstractAnnotatedAggregateRoot<Long> {
	@AggregateIdentifier
	private Long reservationId;
	private Status status;

	@CommandHandler
	public AirReservation(CreateAirReservationCommand command) {
		apply(new AirReservationCreatedEvent(command.getReservationId(),
				Status.NEW));
		apply(new BookingContactUpdatedEvent(command.getReservationId(),
				command.getBookingContact()));
		apply(new ItineraryUpdatedEvent(command.getReservationId(),
				command.getItinerary()));
	}

	@EventHandler
	protected void on(AirReservationCreatedEvent event) {
		this.reservationId = event.getReservationId();
		this.status = event.getStatus();
	}

	public enum Status {
		NEW, BOOKED
	}

	public AirReservation() {
	}

}
