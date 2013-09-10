package com.github.hippoom.ramblings.credit.infrastructure.cqrs;

import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.credit.domain.model.creditaccount.CreditAccountBalanceChangedEvent;
import com.github.hippoom.ramblings.credit.domain.model.creditaccount.CreditAccountCreatedEvent;
import com.github.hippoom.ramblings.credit.query.CreditAccountReadModel;
import com.github.hippoom.ramblings.credit.query.CreditAccountReadModelStore;

@Transactional
@Slf4j
public class CreditAccountEventHandler {

	private CreditAccountReadModelStore creditAccountReadModelStore;

	public void setCreditAccountReadModelStore(
			CreditAccountReadModelStore creditAccountReadModelStore) {
		this.creditAccountReadModelStore = creditAccountReadModelStore;
	}

	@EventHandler
	public void handle(CreditAccountCreatedEvent event) {
		log.info("Received " + event);

		creditAccountReadModelStore.store(account(event));
	}

	@EventHandler
	public void handle(CreditAccountBalanceChangedEvent event) {
		log.info("Received " + event);

		final CreditAccountReadModel account = creditAccountReadModelStore
				.findBy(event.getAccountId());
		account.setBalance(account.getBalance() + event.getAmount());
		creditAccountReadModelStore.store(account);
	}

	private CreditAccountReadModel account(CreditAccountCreatedEvent event) {
		CreditAccountReadModel account = new CreditAccountReadModel();
		account.setId(event.getAccountId());
		account.setEffectiveStart(event.getEffectiveDateRange().getStart());
		account.setEffectiveEnd(event.getEffectiveDateRange().getStart());
		return account;
	}
}
