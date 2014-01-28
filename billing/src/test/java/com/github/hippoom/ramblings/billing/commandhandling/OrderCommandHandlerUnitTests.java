package com.github.hippoom.ramblings.billing.commandhandling;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.billing.commands.AddOrderPaidCommand;
import com.github.hippoom.ramblings.billing.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.billing.domain.events.OrderBalancedEvent;
import com.github.hippoom.ramblings.billing.domain.events.OrderPaidUpdatedEvent;
import com.github.hippoom.ramblings.billing.domain.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.billing.domain.model.order.Order;
import com.github.hippoom.ramblings.billing.domain.shared.IdentifierGenerator;

public class OrderCommandHandlerUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);

	private OrderCommandHandler handler = new OrderCommandHandler();
	@Mock
	private IdentifierGenerator<String> trackingIdGenerator;

	@Before
	public void injects() {
		handler.setTrackingIdGenerator(trackingIdGenerator);
		handler.setOrderRepository(fixture.getRepository());
		fixture.registerAnnotatedCommandHandler(handler);
	}

	@Test
	public void publishesOrderPlacedEvent() throws Throwable {
		final String trackingId = "123";
		final double total = 50;

		context.checking(new Expectations() {
			{
				allowing(trackingIdGenerator).next();
				will(returnValue(trackingId));
			}
		});

		fixture.given().when(new PlaceOrderCommand(total))
				.expectEvents(new OrderPlacedEvent(trackingId, total))
				.expectReturnValue(trackingId);
	}

	@Test
	public void publishesOrderBalancedEvent() throws Throwable {
		final String trackingId = "123";
		final double augend = 50;

		final double total = 50;

		fixture.given(new OrderPlacedEvent(trackingId, total))
				.when(new AddOrderPaidCommand(trackingId, augend))
				.expectEvents(new OrderPaidUpdatedEvent(trackingId, augend),
						new OrderBalancedEvent(trackingId));
	}

	@Test
	public void publishesOrderBalancedEventWhenMultiPaid() throws Throwable {
		final String trackingId = "123";
		final double augend = 50;

		final double total = 100;

		fixture.given(new OrderPlacedEvent(trackingId, total),
				new OrderPaidUpdatedEvent(trackingId, augend))
				.when(new AddOrderPaidCommand(trackingId, augend))
				.expectEvents(new OrderPaidUpdatedEvent(trackingId, total),
						new OrderBalancedEvent(trackingId));
	}

	@Test
	public void balanceDoesNotChangedIfNotFullyPaid() throws Throwable {
		final String trackingId = "123";
		final double augend = 50;

		final double total = 100;

		fixture.given(new OrderPlacedEvent(trackingId, total))
				.when(new AddOrderPaidCommand(trackingId, augend))
				.expectEvents(new OrderPaidUpdatedEvent(trackingId, augend));
	}
}
