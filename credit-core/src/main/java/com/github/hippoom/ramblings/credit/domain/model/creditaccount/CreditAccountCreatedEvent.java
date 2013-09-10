package com.github.hippoom.ramblings.credit.domain.model.creditaccount;

import lombok.ToString;

@ToString
public class CreditAccountCreatedEvent {

	private final Long accountId;
	private final DateRange effectiveDateRange;

	public CreditAccountCreatedEvent(Long accountId,
			DateRange effectiveDateRange) {
		this.accountId = accountId;
		this.effectiveDateRange = effectiveDateRange;
	}

	public Long getAccountId() {
		return accountId;
	}

	public DateRange getEffectiveDateRange() {
		return effectiveDateRange;
	}

}
