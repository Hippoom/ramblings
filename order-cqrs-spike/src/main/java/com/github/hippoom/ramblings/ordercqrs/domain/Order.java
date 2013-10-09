package com.github.hippoom.ramblings.ordercqrs.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.ordercqrs.command.DiscountingCommand;
import com.github.hippoom.ramblings.ordercqrs.command.MakePaymentCommand;
import com.github.hippoom.ramblings.ordercqrs.command.ModifyBookingContactCommand;
import com.github.hippoom.ramblings.ordercqrs.command.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordercqrs.event.BalanceUpdatedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.OrderDiscountedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.OrderPlacedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.PaymentMadeEvent;

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
		this.balance = Balance.whenPlaced(command.getTotalAmount());
		apply(new OrderPlacedEvent(command.getTrackingId(),
				command.getWhenPlaced(), balance.getTotalAmount(),
				balance.getPaid(), balance.getStatus().getValue()));
		apply(new BookingContactUpdatedEvent(command.getTrackingId(),
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
		apply(new BookingContactUpdatedEvent(command.getTrackingId(),
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

		apply(new PaymentMadeEvent(this.trackingId,
				newBalance.getTotalAmount(), newBalance.getPaid(), newBalance
						.getStatus().getValue()));
	}

	/**
	 * <pre>
	 * for domain behavior
	 * for detail read model
	 * for search read model
	 * </pre>
	 */
	@CommandHandler
	protected void handle(DiscountingCommand command) {
		// recalculate balance using rich value object
		final Balance newBalance = this.balance.discountedWith(command
				.getAmount());

		apply(new OrderDiscountedEvent(this.trackingId,
				newBalance.getTotalAmount(), newBalance.getPaid(), newBalance
						.getStatus().getValue()));
	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
		this.balance = Balance.whenPlaced(event.getTotalAmount());
	}

	@EventHandler
	private void on(BalanceUpdatedEvent event) {
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
