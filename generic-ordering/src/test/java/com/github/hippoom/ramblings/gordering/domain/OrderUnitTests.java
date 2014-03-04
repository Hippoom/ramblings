package com.github.hippoom.ramblings.gordering.domain;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.gordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEvent;
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
								rs1),
						new ReservationSpecificationRequestedEvent(trackingId,
								rs2)).expectVoidReturnType();
	}

}
