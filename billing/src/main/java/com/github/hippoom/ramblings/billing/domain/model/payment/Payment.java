package com.github.hippoom.ramblings.billing.domain.model.payment;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.github.hippoom.ramblings.billing.domain.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.billing.domain.events.PaymentMadeEvent;

public class Payment extends AbstractAnnotatedAggregateRoot<String> {

	private static final long serialVersionUID = 1L;

	@AggregateIdentifier
	private String sequence;

	public Payment(String sequence, String orderId, double amount, String by,
			String receiptNo) {
		apply(new PaymentMadeEvent(sequence, orderId, amount, by, receiptNo));
	}

	public void gathered() {
		apply(new PaymentGatheredEvent(sequence));
	}

	@EventHandler
	private void on(PaymentMadeEvent event) {
		this.sequence = event.getSequence();
	}

	/**
	 * for frameworks only
	 */
	@SuppressWarnings("unused")
	private Payment() {
	}

	public String sequence() {
		return this.sequence;
	}

	public static enum By {
		CASH, CHECK, E_PAYMENT
	}

	public static enum Status {
		NEW, GATHERED
	}
}
