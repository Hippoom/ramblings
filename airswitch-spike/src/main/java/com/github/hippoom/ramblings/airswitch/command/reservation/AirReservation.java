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
	private double totalAmount;

	@CommandHandler
	public AirReservation(CreateAirReservationCommand command) {
		apply(new AirReservationCreatedEvent(command.getReservationId(),
				Status.NEW));
		apply(new BookingContactUpdatedEvent(command.getReservationId(),
				command.getBookingContact()));
		apply(new ItineraryUpdatedEvent(command.getReservationId(),
				command.getItinerary()));
	}

	@CommandHandler
	public void sum(CalculateTotalOfReservationCommand command) {
		apply(new AirTicketSubtotalSummedEvent(command.getReservationId(),
				command.getTicketId(), command.getAugend(), totalAmount
						+ command.getAugend()));
	}

	@EventHandler
	protected void on(AirReservationCreatedEvent event) {
		this.reservationId = event.getReservationId();
		this.status = event.getStatus();
	}

	@EventHandler
	protected void on(AirTicketSubtotalSummedEvent event) {
		this.totalAmount = event.getCurrentTotal();
	}

	public enum Status {
		NEW, BOOKED
	}

	public AirReservation() {
	}

}
