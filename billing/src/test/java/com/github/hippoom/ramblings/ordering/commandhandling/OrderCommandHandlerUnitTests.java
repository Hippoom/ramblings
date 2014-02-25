package com.github.hippoom.ramblings.ordering.commandhandling;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.ordering.commands.GatherPaymentCommand;
import com.github.hippoom.ramblings.ordering.commands.MakePaymentCommand;
import com.github.hippoom.ramblings.ordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordering.domain.model.gathering.By;
import com.github.hippoom.ramblings.ordering.domain.model.order.Order;
import com.github.hippoom.ramblings.ordering.domain.shared.IdentifierGenerator;
import com.github.hippoom.ramblings.ordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentMadeEvent;

public class OrderCommandHandlerUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);

	private OrderCommandHandler handler = new OrderCommandHandler();
	@Mock
	private IdentifierGenerator<String> trackingIdGenerator;
	@Mock
	private IdentifierGenerator<String> paymentSequenceGenerator;

	@Before
	public void injects() {
		handler.setTrackingIdGenerator(trackingIdGenerator);
		handler.setSequenceGenerator(paymentSequenceGenerator);
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
	public void publishesPaymentMadeEvent() throws Throwable {
		final String trackingId = "123";
		final double paid = 50;
		final String receiptNo = "21312-123";
		final String by = By.CASH.name();
		final String sequence = "124";

		final double total = 50;

		context.checking(new Expectations() {
			{
				allowing(paymentSequenceGenerator).next();
				will(returnValue(sequence));
			}
		});

		fixture.given(new OrderPlacedEvent(trackingId, total))
				.when(new MakePaymentCommand(trackingId, paid, by, receiptNo))
				.expectEvents(
						new PaymentMadeEvent(sequence, trackingId, paid,
								Order.BalanceStatus.BALANCED.name(), by,
								receiptNo));
	}

	@Test
	public void publishesPaymentGatheredEvent() throws Throwable {
		final String trackingId = "123";
		final double paid = 50;
		final String receiptNo = "21312-123";
		final String by = By.CASH.name();
		final String sequence = "124";

		final double total = 50;

		context.checking(new Expectations() {
			{
				allowing(paymentSequenceGenerator).next();
				will(returnValue(sequence));
			}
		});

		fixture.given(
				new OrderPlacedEvent(trackingId, total),
				new PaymentMadeEvent(sequence, trackingId, paid,
						Order.BalanceStatus.BALANCED.name(), by, receiptNo))
				.when(new GatherPaymentCommand(trackingId, sequence))
				.expectEvents(new PaymentGatheredEvent(sequence))
				.expectReturnValue(sequence);
	}
}
