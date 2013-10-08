package com.github.hippoom.ramblings.ordercrqs.command;

import java.math.BigDecimal;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.ordercrqs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.OrderPlacedEvent;

public class OrderUnitTests {
	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);
	private String trackingId = "1";
	private String bookingContact = "John Doe";
	private BigDecimal totalAmount = BigDecimal.valueOf(100);

	@Test
	public void publishesOrderPlacedEventWhenHandlesPlaceOrderCommand()
			throws Throwable {
		fixture.given()
				.when(new PlaceOrderCommand(trackingId, bookingContact,
						totalAmount))
				.expectEvents(
						new OrderPlacedEvent(trackingId, bookingContact,
								totalAmount));

	}

	@Test
	public void publishesBookingContactUpdatedEventWhenHandlesUpdateBookingContactCommand()
			throws Throwable {
		final String newBookingContact = "Geroge O'Malley";

		fixture.given(
				new OrderPlacedEvent(trackingId, bookingContact, totalAmount))
				.when(new UpdateBookingContactCommand(trackingId,
						newBookingContact))
				.expectEvents(
						new BookingContactUpdatedEvent(trackingId,
								newBookingContact));

	}

}
