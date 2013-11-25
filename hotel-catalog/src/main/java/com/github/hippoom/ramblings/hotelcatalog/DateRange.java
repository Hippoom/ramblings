package com.github.hippoom.ramblings.hotelcatalog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.joda.time.DateTime;
import org.joda.time.Days;

@EqualsAndHashCode
@ToString
public class DateRange {
	private final Date start;
	private final Date end;

	private DateRange(Date start, Date end) {
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());
	}

	public static DateRange at(String date) {
		return of(date, date);
	}

	public static DateRange of(Date start, Date end) {
		return new DateRange(start, end);
	}

	public static DateRange of(String start, String end) {
		try {
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			return new DateRange(simpleDateFormat.parse(start),
					simpleDateFormat.parse(end));
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Date start() {
		return new Date(start.getTime());
	}

	public Date end() {
		return new Date(end.getTime());
	}

	/**
	 * 
	 * @return 1 if one day
	 */
	public int days() {
		final DateTime start = new DateTime(this.start);
		final DateTime end = new DateTime(this.end);

		return Days.daysBetween(start, end).getDays() + 1;
	}
}
