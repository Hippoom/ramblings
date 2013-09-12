package com.github.hippoom.ramblings.credit.domain.model.creditaccount;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class DateRange {
	public Date start;
	public Date end;

	public DateRange(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public boolean covers(Date now) {
		return (getStart().compareTo(now) <= 0)
				&& (getEnd().compareTo(now) >= 0);
	}
}