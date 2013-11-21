package com.github.hippoom.ramblings.ordercqrs.saga;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.github.hippoom.ramblings.ordercqrs.domain.BalanceStatus;
import com.github.hippoom.ramblings.ordercqrs.event.PaymentMadeEvent;

public class NotifyCustomerSagaUnitTests {
	private AnnotatedSagaTestFixture fixture = new AnnotatedSagaTestFixture(
			NotifyCustomerSaga.class);

	private MailSenderStub mailSender = new MailSenderStub();

	@Before
	public void inject() {
		fixture.registerCommandGateway(CommandGateway.class);

		fixture.registerResource(mailSender);
	}

	@Test
	public void sendsEmailWhenBalanced() throws Throwable {

		final String trackingId = "1";
		final BigDecimal total = BigDecimal.valueOf(100.00);
		final BigDecimal paid = BigDecimal.valueOf(100.00);
		final String balanceStatus = BalanceStatus.BALANCED.getValue();

		fixture.whenPublishingA(
				new PaymentMadeEvent(trackingId, total, paid, balanceStatus))
				.expectActiveSagas(0);

		assertThat(mailSender.getTrackingId(), equalTo(trackingId));
	}

	@Test
	public void waitWhenUnbalancedStill() throws Throwable {

		final String trackingId = "1";
		final BigDecimal total = BigDecimal.valueOf(100.00);
		final BigDecimal paid = BigDecimal.valueOf(20.00);
		final String balanceStatus = BalanceStatus.UNBALANCED.getValue();

		fixture.whenPublishingA(
				new PaymentMadeEvent(trackingId, total, paid, balanceStatus))
				.expectActiveSagas(1);

	}

	@Test
	public void onlyOneSageFiredWhenMultiplePaymentMade() throws Throwable {

		final String trackingId = "1";
		final BigDecimal total = BigDecimal.valueOf(100.00);
		final BigDecimal paid = BigDecimal.valueOf(20.00);
		final String balanceStatus = BalanceStatus.UNBALANCED.getValue();

		fixture.givenAPublished(
				new PaymentMadeEvent(trackingId, total, paid, balanceStatus))
				.whenPublishingA(
						new PaymentMadeEvent(trackingId, total, paid,
								balanceStatus)).expectActiveSagas(1);

	}

}
