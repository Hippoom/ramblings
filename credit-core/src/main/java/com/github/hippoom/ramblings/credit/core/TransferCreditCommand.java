package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class TransferCreditCommand {
	@TargetAggregateIdentifier
	private final Long accountId;
	private final int amount;
	private final Date now;

	public TransferCreditCommand(Long accountId, int amount, Date now) {
		this.accountId = accountId;
		this.amount = amount;
		this.now = now;
	}

	public Long getAccountId() {
		return accountId;
	}

	public int getAmount() {
		return amount;
	}

	public Date getNow() {
		return now;
	}
}
