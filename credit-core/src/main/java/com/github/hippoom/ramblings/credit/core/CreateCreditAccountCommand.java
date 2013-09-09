package com.github.hippoom.ramblings.credit.core;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateCreditAccountCommand {

	@TargetAggregateIdentifier
	private final Long accountId;

	private final int amount;

	public CreateCreditAccountCommand(Long accountId, int amount) {
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
