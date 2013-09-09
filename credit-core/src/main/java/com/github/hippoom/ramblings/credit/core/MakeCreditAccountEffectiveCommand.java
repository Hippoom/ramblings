package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class MakeCreditAccountEffectiveCommand {
	@TargetAggregateIdentifier
	private final Long entryId;
	private final Date start;
	private final Date end;

	public MakeCreditAccountEffectiveCommand(Long entryId, Date start, Date end) {
		this.entryId = entryId;
		this.start = start;
		this.end = end;
	}

	public Long getEntryId() {
		return entryId;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
}
