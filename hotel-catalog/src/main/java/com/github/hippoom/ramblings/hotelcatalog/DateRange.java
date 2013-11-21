package com.github.hippoom.ramblings.hotelcatalog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRange {
	private final Date start;
	private final Date end;

	public DateRange(String start, String end) {
		try {
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			this.start = simpleDateFormat.parse(start);
			this.end = simpleDateFormat.parse(end);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
