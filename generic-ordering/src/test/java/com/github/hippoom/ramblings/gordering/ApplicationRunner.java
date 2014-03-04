package com.github.hippoom.ramblings.gordering;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hippoom.ramblings.gordering.commands.OrderingService;
import com.github.hippoom.ramblings.gordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.gordering.query.OrderView;
import com.github.hippoom.ramblings.gordering.test.InMemoryOrderQueryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root.xml" })
public class ApplicationRunner {
	@Autowired
	private OrderingService orderingService;
	@Autowired
	private InMemoryOrderQueryService orderQueryService;

	@Test
	public void placesOrderForHotelReservation() throws Throwable {
		final String trackingId = "trackingId1";

		orderingService.send(new PlaceOrderCommand(trackingId,
				"some vip member"));

		final OrderView order = orderQueryService.trackingIdAs(trackingId);
		assertThat(order, is(not(nullValue())));
	}
}
