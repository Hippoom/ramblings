package com.github.hippoom.ramblings.ordercrqs.command;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.ordercrqs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.OrderPlacedEvent;

public class OrderUnitTests {
	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);

	@Test
	public void publishesBookingContactUpdatedEvent() throws Throwable {
		final String trackingId = "1";
		final String originBookingContact = "John Doe";
		final String newBookingContact = "Geroge O'Malley";

		fixture.given(new OrderPlacedEvent(trackingId, originBookingContact))
				.when(new UpdateBookingContactCommand(trackingId,
						newBookingContact))
				.expectEvents(
						new BookingContactUpdatedEvent(trackingId,
								newBookingContact));

	}

}
