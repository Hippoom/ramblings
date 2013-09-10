package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class CreditAccount extends AbstractAnnotatedAggregateRoot<Long> {

	private static final long serialVersionUID = 1L;

	@AggregateIdentifier
	private Long id;
	private int balance;
	private DateRange effectiveDateRange;

	@CommandHandler
	public CreditAccount(CreateCreditAccountCommand command) {
		apply(new CreditAccountCreatedEvent(command.getAccountId(),
				command.getEffectiveDateRange()));
		apply(new CreditAccountBalanceChangedEvent(command.getAccountId(),
				command.getAmount()));
	}

	@EventHandler
	public void on(CreditAccountCreatedEvent event) {
		this.id = event.getAccountId();
		this.effectiveDateRange = event.getEffectiveDateRange();
	}

	@CommandHandler
	public void transfer(ConsumeCreditCommand command) {
		if (isEffectiveFor(command.getNow())) {
			if (enoughCreditsFor(command.getAmountConsumed())) {
				apply(new CreditAccountBalanceChangedEvent(
						command.getAccountId(), command.getAmountConsumed()));
			} else {
				throw new IllegalStateException(
						"Cannot transfer creidts for Account[" + this.id
								+ "] due to not enough credits. Balance="
								+ this.balance + ", amount="
								+ command.getAmount());
			}
		} else {
			throw new IllegalStateException(
					"Cannot transfer creidts for Account[" + this.id
							+ "], it's not effective.");
		}
	}

	private boolean isEffectiveFor(Date now) {
		return getEffectiveDateRange().covers(now);
	}

	private boolean enoughCreditsFor(int amount) {
		final int balance = add(this.balance, amount);
		return balance >= 0;
	}

	@EventHandler
	public void on(CreditAccountBalanceChangedEvent event) {
		this.balance = add(this.balance, event.getAmount());
	}

	private int add(int current, int amount) {
		return current + amount;
	}

	public CreditAccount() {

	}

	public Long getId() {
		return id;
	}

	public int getBalance() {
		return balance;
	}

	public DateRange getEffectiveDateRange() {
		return effectiveDateRange;
	}

}
