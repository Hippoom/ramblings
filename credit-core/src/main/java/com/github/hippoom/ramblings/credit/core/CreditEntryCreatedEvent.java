package com.github.hippoom.ramblings.credit.core;

public class CreditEntryCreatedEvent {

	private final Long entryId;

	private final int amount;

	public CreditEntryCreatedEvent(Long entryId, int amount) {
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
