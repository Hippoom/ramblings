package com.github.hippoom.ramblings.credit.core;

import static com.github.hippoom.ramblings.credit.core.CreditAccount.Status.*;

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

	private Status status;

	@CommandHandler
	public CreditAccount(CreateCreditAccountCommand command) {
		apply(new CreditAccountCreatedEvent(command.getAccountId(),
				command.getAmount()));
	}

	@EventHandler
	public void on(CreditAccountCreatedEvent event) {
		this.id = event.getAccountId();
		this.balance = event.getAmount();
		this.status = Status.NEW;
	}

	@CommandHandler
	public void makeEffective(MakeCreditAccountEffectiveCommand command) {
		apply(new CreditAccountMadeEffectiveEvent(command.getStart(),
				command.getEnd()));
	}

	@EventHandler
	public void on(CreditAccountMadeEffectiveEvent event) {
		this.effectiveDateRange = new DateRange(event.getStart(),
				event.getEnd());
		this.status = Status.EFFECTIVE;
	}

	@CommandHandler
	public void transfer(TransferCreditCommand command) {
		if (isEffectiveFor(command.getNow())) {
			if (enoughCreditsFor(command.getAmount())) {
				apply(new CreditAccountBalanceChangedEvent(command.getAmount()));
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
		return isEffective() && getEffectiveDateRange().covers(now);
	}

	private boolean enoughCreditsFor(int amount) {
		final int balance = add(this.balance, amount);
		return balance >= 0;
	}

	@EventHandler
	public void on(CreditAccountBalanceChangedEvent event) {
		this.balance = add(this.balance, event.getAmount());
	}

	@CommandHandler
	public void expire(ExpireCreditAccountCommand command) {
		apply(new CreditAccountExpiredEvent(command.getAccountId()));
	}

	@EventHandler
	public void on(CreditAccountExpiredEvent event) {
		this.status = Status.EXPIRED;
	}

	private int add(int current, int amount) {
		return current + amount;
	}

	public CreditAccount() {

	}

	public enum Status {
		NEW, EFFECTIVE, EXPIRED
	}

	public boolean isNew() {
		return is(NEW);
	}

	public boolean isEffective() {
		return is(EFFECTIVE);
	}

	private boolean is(Status status) {
		return this.status == status;
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

	public Status getStatus() {
		return status;
	}
}
