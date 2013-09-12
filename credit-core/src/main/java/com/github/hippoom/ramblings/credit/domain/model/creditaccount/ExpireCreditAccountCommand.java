package com.github.hippoom.ramblings.credit.domain.model.creditaccount;

import lombok.ToString;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@ToString
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
