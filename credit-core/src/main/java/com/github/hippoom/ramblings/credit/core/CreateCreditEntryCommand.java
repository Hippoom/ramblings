package com.github.hippoom.ramblings.credit.core;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateCreditEntryCommand {

	@TargetAggregateIdentifier
	private final Long entryId;

	private final int amount;

	public CreateCreditEntryCommand(Long entryId, int amount) {
		this.entryId = entryId;
		this.amount = amount;
	}

	public Long getEntryId() {
		return entryId;
	}

	public int getAmount() {
		return amount;
	}

}
