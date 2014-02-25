package com.github.hippoom.ramblings.billing.query;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.billing.query.ReceiptViewStore;
import com.github.hippoom.ramblings.billing.query.ReceiptViewSynchronizer;
import com.github.hippoom.ramblings.ordering.domain.model.gathering.By;
import com.github.hippoom.ramblings.ordering.domain.model.order.Order;
import com.github.hippoom.ramblings.ordering.events.PaymentMadeEvent;

public class ReceiptViewSynchronizerUnitTests {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	private ReceiptViewSynchronizer handler = new ReceiptViewSynchronizer();

	@Mock
	private ReceiptViewStore receiptViewStore;

	@Before
	public void injects() {
		handler.setReceiptViewStore(receiptViewStore);
	}

	@Test
	public void updatesReceiptAmountByAddingAmountPaid() throws Throwable {
		final String orderId = "123";
		final double amount = 50;
		final String receiptNo = "21312-123";

		final String sequence = "124";

		context.checking(new Expectations() {
			{
				oneOf(receiptViewStore).addAmount(receiptNo, amount);
			}
		});

		handler.on(new PaymentMadeEvent(sequence, orderId, amount,
				Order.BalanceStatus.BALANCED.name(), By.CASH.name(), receiptNo));
	}
}
