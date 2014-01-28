package com.github.hippoom.ramblings.billing.commandhandling;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.billing.commands.GatherPaymentCommand;
import com.github.hippoom.ramblings.billing.commands.MakePaymentCommand;
import com.github.hippoom.ramblings.billing.domain.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.billing.domain.events.PaymentMadeEvent;
import com.github.hippoom.ramblings.billing.domain.model.payment.Payment;
import com.github.hippoom.ramblings.billing.domain.shared.IdentifierGenerator;

public class PaymentCommandHandlerUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	private FixtureConfiguration<Payment> fixture = Fixtures
			.newGivenWhenThenFixture(Payment.class);

	private PaymentCommandHandler handler = new PaymentCommandHandler();
	@Mock
	private IdentifierGenerator<String> paymentSequenceGenerator;

	@Before
	public void injects() {

		handler.setPaymentSequenceGenerator(paymentSequenceGenerator);
		handler.setPaymentRepository(fixture.getRepository());
		fixture.registerAnnotatedCommandHandler(handler);
	}

	@Test
	public void publishesPaymentMadeEvent() throws Throwable {
		final String orderId = "123";
		final double amount = 50;
		final String receiptNo = "21312-123";
		final String by = Payment.By.CASH.name();

		final String sequence = "124";

		context.checking(new Expectations() {
			{
				allowing(paymentSequenceGenerator).next();
				will(returnValue(sequence));
			}
		});

		fixture.given()
				.when(new MakePaymentCommand(orderId, amount, by, receiptNo))
				.expectEvents(
						new PaymentMadeEvent(sequence, orderId, amount, by,
								receiptNo)).expectReturnValue(sequence);
	}

	@Test
	public void publishesPaymentGatheredEvent() throws Throwable {
		final String orderId = "123";
		final double amount = 50;
		final String receiptNo = "21312-123";
		final String by = Payment.By.CASH.name();

		final String sequence = "124";

		fixture.given(
				new PaymentMadeEvent(sequence, orderId, amount, by, receiptNo))
				.when(new GatherPaymentCommand(sequence))
				.expectEvents(new PaymentGatheredEvent(sequence));
	}
}
