package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class MakeCreditAccountEffectiveCommand {
	@TargetAggregateIdentifier
	private final Long accountId;
	private final Date start;
	private final Date end;

	public MakeCreditAccountEffectiveCommand(Long entryId, Date start, Date end) {
		this.accountId = entryId;
		this.start = start;
		this.end = end;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
}
