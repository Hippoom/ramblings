package com.github.hippoom.ramblings.hotelcatalog;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RoomStayUnitTests {
	@Test
	public void returnsAverageRateGivenDailyRate() throws Throwable {
		final RoomRate roomRate1 = new RoomRate("STD", "BAR", new Rate(100,
				"2013-04-01"));
		final RoomRate roomRate2 = new RoomRate("DLX", "BAR", new Rate(120,
				"2013-04-01"));
		final RoomRate roomRate3 = new RoomRate("SPR", "BAR", new Rate(105,
				"2013-04-01"));
		final RoomStay roomStay = new RoomStay("Garden Hotel", roomRate1,
				roomRate2, roomRate3);

		assertThat(roomStay.lowestAverageAmountBeforeTax(), equalTo(100.0));
	}

}
