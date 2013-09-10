package com.github.hippoom.ramblings.credit.domain.model.creditaccount;

import lombok.ToString;

@ToString
public class CreditAccountBalanceChangedEvent {
	private final long accountId;
	private final int amount;

	public CreditAccountBalanceChangedEvent(long accountId, int amount) {
		this.accountId = accountId;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public long getAccountId() {
		return accountId;
	}
}
