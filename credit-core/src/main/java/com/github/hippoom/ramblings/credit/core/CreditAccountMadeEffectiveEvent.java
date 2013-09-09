package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

public class CreditAccountMadeEffectiveEvent {

	private final Date start;
	private final Date end;

	public CreditAccountMadeEffectiveEvent(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
}
