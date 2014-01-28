package com.github.hippoom.ramblings.billing.commandhandling;

import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.billing.commands.AddOrderPaidCommand;
import com.github.hippoom.ramblings.billing.domain.events.PaymentMadeEvent;
import com.github.hippoom.ramblings.billing.domain.model.payment.Payment;
import com.github.hippoom.ramblings.billing.domain.saga.OrderBalanceSaga;

public class OrderBalanceSagaUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	private AnnotatedSagaTestFixture fixture = new AnnotatedSagaTestFixture(
			OrderBalanceSaga.class);

	@Before
	public void injects() {

	}

	@Test
	public void publishesPaymentMadeEvent() throws Throwable {
		final String orderId = "123";
		final double amount = 50;
		final String receiptNo = "21312-123";

		final String sequence = "124";

		fixture.whenPublishingA(
				new PaymentMadeEvent(sequence, orderId, amount, Payment.By.CASH
						.name(), receiptNo))
				.expectDispatchedCommandsEqualTo(
						new AddOrderPaidCommand(orderId, amount))
				.expectActiveSagas(0);
	}
}
