package com.github.hippoom.ramblings.ordering.domain.model.order;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.ordering.domain.model.gathering.By;
import com.github.hippoom.ramblings.ordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentMadeEvent;

public class Order extends AbstractAnnotatedAggregateRoot<String> {

	private static final long serialVersionUID = 1L;
	@AggregateIdentifier
	private String trackingId;

	private double totalAmount;

	private double totalPaid;

	private BalanceStatus balanceStatus;

	public Order(String trackingId, double totalAmount) {
		apply(new OrderPlacedEvent(trackingId, totalAmount));
	}

	public void makePaymentWith(String next, double amount, By by,
			String receiptNo) {
		addPaid(amount);
		apply(new PaymentMadeEvent(next, this.trackingId, amount,
				balanceStatus.name(), by.name(), receiptNo));
	}

	private void addPaid(double augend) {
		double totalPaid = this.totalPaid + augend;

		if (totalAmount == totalPaid) {
			this.balanceStatus = BalanceStatus.BALANCED;
		}

	}

	public void gatherPaymentWith(String sequence) {
		
		apply(new PaymentGatheredEvent(sequence));
	}

	@EventHandler
	private void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
		this.totalAmount = event.getTotal();
		this.balanceStatus = BalanceStatus.UNBALANCED;
	}

	@EventHandler
	private void on(PaymentMadeEvent event) {
		this.totalPaid += event.getAmount();
		this.balanceStatus = BalanceStatus.valueOf(event.getStatus());
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

	public enum BalanceStatus {
		UNBALANCED, BALANCED
	}

}
