package com.github.hippoom.ramblings.billing.query;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.github.hippoom.ramblings.billing.domain.events.PaymentMadeEvent;

@Slf4j
public class ReceiptViewSynchronizer {
	@Setter
	private ReceiptViewStore receiptViewStore;

	@EventHandler
	public void on(PaymentMadeEvent event) {
		log.debug("Receiving " + event.toString());
		receiptViewStore.addAmount(event.getReceiptNo(), event.getAmount());
	}

}
