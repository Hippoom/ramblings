package com.github.hippoom.ramblings.ordercrqs.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.ordercrqs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.OrderPlacedEvent;

@SuppressWarnings("serial")
public class Order extends AbstractAnnotatedAggregateRoot<String> {
	@AggregateIdentifier
	private String trackingId;

	@CommandHandler
	public Order(PlaceOrderCommand command) {
		apply(new OrderPlacedEvent(command.getTrackingId(),
				command.getBookingContact(), command.getTotalAmount()));
	}

	@CommandHandler
	private void handle(UpdateBookingContactCommand command) {
		apply(new BookingContactUpdatedEvent(command.getTrackingId(),
				command.getBookingContact()));
	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
	}

	/**
	 * for frameworks only
	 */
	@SuppressWarnings("unused")
	private Order() {
	}

}
