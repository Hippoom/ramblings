package com.github.hippoom.ramblings.gordering.domain;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.gordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderAsWaitPaymentCommand;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderLineAmountCommand;
import com.github.hippoom.ramblings.gordering.events.OrderFullfilledEvent;
import com.github.hippoom.ramblings.gordering.events.OrderLineAmountUpdatedEvent;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEventFixture;
import com.github.hippoom.ramblings.gordering.events.ReservationSpecificationRequestedEvent;

public class OrderUnitTests {

	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);

	@Test
	public void publishesOrderPlacedEvent() throws Throwable {
		final String trackingId = "123";
		final String memberId = "456";
		final String rs1 = "rs1";
		final String rs2 = "rs2";

		fixture.given()
				.when(new PlaceOrderCommand(trackingId, memberId, rs1, rs2))
				.expectEvents(
						new OrderPlacedEvent(trackingId, memberId,
								Order.Status.FULLFILLING.name()),
						new ReservationSpecificationRequestedEvent(trackingId,
								1, rs1),
						new ReservationSpecificationRequestedEvent(trackingId,
								2, rs2)).expectVoidReturnType();
	}

	@Test
	public void publishesOrderLineAmountUpdatedEvent() throws Throwable {
		final String trackingId = "123";

		fixture.given(
				new OrderPlacedEventFixture(trackingId).build(),
				new ReservationSpecificationRequestedEvent(trackingId, 1, "rs1"))
				.when(new UpdateOrderLineAmountCommand(trackingId, 1, 100.0))
				.expectEvents(
						new OrderLineAmountUpdatedEvent(trackingId, 1, 100.0))
				.expectVoidReturnType();
	}

	@Test
	public void publishesOrderFullfilledEvent() throws Throwable {
		final String trackingId = "123";

		fixture.given(
				new OrderPlacedEventFixture(trackingId).build(),
				new ReservationSpecificationRequestedEvent(trackingId, 1, "rs1"))
				.when(new UpdateOrderAsWaitPaymentCommand(trackingId))
				.expectEvents(
						new OrderFullfilledEvent(trackingId,
								Order.Status.WAIT_PAYMENT.name()))
				.expectVoidReturnType();
	}
}
