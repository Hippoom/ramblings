package com.github.hippoom.ramblings.ordercrqs.domain;


import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.ordercrqs.command.MakePaymentCommand;
import com.github.hippoom.ramblings.ordercrqs.command.ModifyBookingContactCommand;
import com.github.hippoom.ramblings.ordercrqs.command.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordercrqs.event.BalanceRecalculatedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.BookingContactGivenEvent;
import com.github.hippoom.ramblings.ordercrqs.event.BookingContactModifiedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.OrderPlacedEvent;

/**
 * 
 * focus on the behavior, not state
 * 
 */
@SuppressWarnings("serial")
public class Order extends AbstractAnnotatedAggregateRoot<String> {
	@AggregateIdentifier
	private String trackingId;

	private Balance balance;

	/**
	 * <pre>
	 * for daily sales report read model
	 * for detail read model
	 * for search read model
	 * </pre>
	 */
	@CommandHandler
	public Order(PlaceOrderCommand command) {
		apply(new OrderPlacedEvent(command.getTrackingId(),
				command.getWhenPlaced(), command.getTotalAmount()));
		apply(new BookingContactGivenEvent(command.getTrackingId(),
				command.getBookingContact()));
	}

	/**
	 * <pre>
	 * for detail read model
	 * for search read model
	 * </pre>
	 */
	@CommandHandler
	protected void handle(ModifyBookingContactCommand command) {
		apply(new BookingContactModifiedEvent(command.getTrackingId(),
				command.getBookingContact()));
	}

	/**
	 * <pre>
	 * for domain behavior
	 * for detail read model
	 * for search read model
	 * </pre>
	 */
	@CommandHandler
	protected void handle(MakePaymentCommand command) {
		// recalculate balance using rich value object
		final Balance newBalance = this.balance.paidWith(command.getAmount());

		apply(new BalanceRecalculatedEvent(command.getTrackingId(),
				newBalance.getTotalAmount(), newBalance.getPaid(), newBalance
						.getStatus().getValue()));
	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
		this.balance = Balance.whenPlaced(event.getTotalAmount());
	}

	@EventHandler
	private void on(BalanceRecalculatedEvent event) {
		this.balance = Balance.reconsititue(event.getTotalAmount(),
				event.getPaid());
	}

	/**
	 * for frameworks only
	 */
	@SuppressWarnings("unused")
	private Order() {
	}
}
