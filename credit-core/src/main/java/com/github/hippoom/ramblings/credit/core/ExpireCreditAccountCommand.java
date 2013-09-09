package com.github.hippoom.ramblings.credit.core;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ExpireCreditAccountCommand {
	@TargetAggregateIdentifier
	private final Long accountId;

	public ExpireCreditAccountCommand(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}
}
