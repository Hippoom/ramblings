package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

public class DateRange {
	public Date effectiveDateRangeStart;
	public Date effectiveDateRangeEnd;

	public DateRange(Date effectiveDateRangeStart, Date effectiveDateRangeEnd) {
		this.effectiveDateRangeStart = effectiveDateRangeStart;
		this.effectiveDateRangeEnd = effectiveDateRangeEnd;
	}

	public Date getEffectiveDateRangeStart() {
		return effectiveDateRangeStart;
	}

	public Date getEffectiveDateRangeEnd() {
		return effectiveDateRangeEnd;
	}

	public boolean covers(Date now) {
		return (getEffectiveDateRangeStart().compareTo(now) <= 0)
				&& (getEffectiveDateRangeEnd().compareTo(now) >= 0);
	}
}