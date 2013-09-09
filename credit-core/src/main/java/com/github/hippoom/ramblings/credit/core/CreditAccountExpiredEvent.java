package com.github.hippoom.ramblings.credit.core;

public class CreditAccountExpiredEvent {
	private final Long accountId;

	public CreditAccountExpiredEvent(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}
}
