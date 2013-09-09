package com.github.hippoom.ramblings.credit.core;

public class CreditAccountCreatedEvent {

	private final Long accountId;

	private final int amount;

	public CreditAccountCreatedEvent(Long accountId, int amount) {
		this.accountId = accountId;
		this.amount = amount;
	}

	public Long getAccountId() {
		return accountId;
	}

	public int getAmount() {
		return amount;
	}

}
