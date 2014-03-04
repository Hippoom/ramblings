package com.github.hippoom.ramblings.gordering.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.gordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.gordering.events.ReservationSpecificationRequestedEvent;

public class Order extends AbstractAnnotatedAggregateRoot<String> {

	private static final long serialVersionUID = 1L;

	@AggregateIdentifier
	private String trackingId;

	@CommandHandler
	public Order(PlaceOrderCommand command) {
		apply(new OrderPlacedEvent(command.getTrackingId(),
				command.getMemberId(), Status.FULLFILLING.name()));
		for (String rs : command.getReservationSpecifications()) {
			apply(new ReservationSpecificationRequestedEvent(
					command.getTrackingId(), rs));
		}
	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
	}

	/**
	 * for framework only
	 */
	public Order() {
	}

	public static enum Status {
		FULLFILLING
	}
}
