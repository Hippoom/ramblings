package com.github.hippoom.ramblings.hotelcatalog;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DateRangeUnitTests {
	@Test
	public void sameDate() throws Throwable {
		DateRange dr = DateRange.of("2013-04-01", "2013-04-01");

		assertThat(dr.days(), equalTo(1));
	}

	@Test
	public void todayAndTomorrow() throws Throwable {
		DateRange dr = DateRange.of("2013-04-01", "2013-04-02");

		assertThat(dr.days(), equalTo(2));
	}

	@Test
	public void threeDays() throws Throwable {
		DateRange dr = DateRange.of("2013-04-01", "2013-04-03");

		assertThat(dr.days(), equalTo(3));
	}

	@Test
	public void differentMonths() throws Throwable {
		DateRange dr = DateRange.of("2013-03-31", "2013-04-03");

		assertThat(dr.days(), equalTo(4));
	}
}
