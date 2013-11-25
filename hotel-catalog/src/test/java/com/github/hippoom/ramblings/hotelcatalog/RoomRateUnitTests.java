package com.github.hippoom.ramblings.hotelcatalog;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RoomRateUnitTests {
	@Test
	public void returnsAverageRateGivenDailyRate() throws Throwable {
		final RoomRate roomRate = new RoomRate("STD", "BAR", new Rate(100,
				"2013-04-01"));

		assertThat(roomRate.averageAmountBeforeTax(), equalTo(100.0));
		assertThat(roomRate.dateRange(), equalTo(DateRange.at("2013-04-01")));
	}

	@Test
	public void returnsAverageRateGivenDailyRates() throws Throwable {
		final RoomRate roomRate = new RoomRate("STD", "BAR", new Rate(100,
				"2013-04-01"), new Rate(110, "2013-04-02"));

		assertThat(roomRate.averageAmountBeforeTax(), equalTo(105.0));
		assertThat(roomRate.dateRange(),
				equalTo(DateRange.of("2013-04-01", "2013-04-02")));
	}

	@Test
	public void returnsRateGivenConsecutiveRates() throws Throwable {
		final RoomRate roomRate = new RoomRate("STD", "BAR", new Rate(120,
				"2013-04-01", "2013-04-02"));

		assertThat(roomRate.averageAmountBeforeTax(), equalTo(60.0));
		assertThat(roomRate.dateRange(),
				equalTo(DateRange.of("2013-04-01", "2013-04-02")));
	}

}
