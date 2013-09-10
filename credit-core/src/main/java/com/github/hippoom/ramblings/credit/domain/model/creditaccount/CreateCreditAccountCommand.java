package com.github.hippoom.ramblings.credit.domain.model.creditaccount;

import java.util.Date;

import lombok.ToString;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

@ToString
public class CreateCreditAccountCommand {

	@TargetAggregateIdentifier
	private final Long accountId;

	private final int amount;

	private final DateRange effectiveDateRange;

	public CreateCreditAccountCommand(Long accountId, int amount, Date start,
			Date end) {
		this.accountId = accountId;
		this.amount = amount;
		this.effectiveDateRange = new DateRange(start, end);
	}

	public Long getAccountId() {
		return accountId;
	}

	public int getAmount() {
		return amount;
	}

	public DateRange getEffectiveDateRange() {
		return effectiveDateRange;
	}

}
