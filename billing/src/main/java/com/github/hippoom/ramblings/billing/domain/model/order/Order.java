package com.github.hippoom.ramblings.billing.domain.model.order;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.billing.domain.events.OrderBalancedEvent;
import com.github.hippoom.ramblings.billing.domain.events.OrderPaidUpdatedEvent;
import com.github.hippoom.ramblings.billing.domain.events.OrderPlacedEvent;

public class Order extends AbstractAnnotatedAggregateRoot<String> {

	private static final long serialVersionUID = 1L;
	@AggregateIdentifier
	private String trackingId;

	private double totalAmount;

	private double totalPaid;

	public Order(String trackingId, double totalAmount) {
		apply(new OrderPlacedEvent(trackingId, totalAmount));
	}

	public void addPaid(double augend) {
		double totalPaid = this.totalPaid + augend;
		apply(new OrderPaidUpdatedEvent(this.trackingId, totalPaid));

		if (totalAmount == totalPaid) {
			apply(new OrderBalancedEvent(this.trackingId));
		}

	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
		this.totalAmount = event.getTotal();
	}

	@EventHandler
	private void on(OrderPaidUpdatedEvent event) {
		this.trackingId = event.getTrackingId();
		this.totalPaid = event.getTotal();
	}

	/**
	 * for frameworks only
	 */
	@SuppressWarnings("unused")
	private Order() {
	}

	public String trackingId() {
		return trackingId;
	}
}
