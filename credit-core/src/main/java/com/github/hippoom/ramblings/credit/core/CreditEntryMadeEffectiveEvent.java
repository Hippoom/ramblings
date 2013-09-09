package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

public class CreditEntryMadeEffectiveEvent {
	private final Long entryId;
	private final Date start;
	private final Date end;

	public CreditEntryMadeEffectiveEvent(Long entryId, Date start, Date end) {
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
