package com.github.hippoom.ramblings.billing;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hippoom.ramblings.billing.commands.GatherPaymentCommand;
import com.github.hippoom.ramblings.billing.commands.MakePaymentCommand;
import com.github.hippoom.ramblings.billing.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.billing.domain.model.payment.Payment;
import com.github.hippoom.ramblings.billing.query.GatheringView;
import com.github.hippoom.ramblings.billing.query.GatheringViewStore;
import com.github.hippoom.ramblings.billing.query.ReceiptView;
import com.github.hippoom.ramblings.billing.query.ReceiptViewStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root.xml" })
public class ApplicationRunner {
	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private ReceiptViewStore receipts;

	@Autowired
	private GatheringViewStore orders;

	@SuppressWarnings("unchecked")
	@Test
	public void run() throws Throwable {
		final double total = 50;
		final String receiptNo = "123-456";
		final double paidByCash = 10;
		final double paidByCheck = 20;

		final ReceiptView receiptBefore = findReceiptBy(receiptNo);

		final String trackingId = placeOrderWith(total);

		makePaymentByCashWith(trackingId, paidByCash, receiptNo);

		final String toBeGathered = makePaymentByCheckWith(trackingId,
				paidByCheck, receiptNo);

		final ReceiptView receiptAfter = findReceiptBy(receiptNo);

		assertThat(receiptAfter.getAmount() - paidByCash - paidByCheck,
				equalTo(receiptBefore == null ? 0 : receiptBefore.getAmount()));

		GatheringView order = orders.trackingIdAs(trackingId);

		assertThat(order.getTotal(), equalTo(total));
		assertThat(order.getReceipts(), contains(receiptNo));
		assertThat(order.getPaid(), hasEntry("CASH", paidByCash));
		assertThat(order.getPaid(), hasEntry("CHECK", paidByCheck));

		commandGateway.sendAndWait(new GatherPaymentCommand(toBeGathered));

		GatheringView orderAfterCheckGathered = orders.trackingIdAs(trackingId);

		assertThat(orderAfterCheckGathered.getPaid(),
				hasEntry("CASH", paidByCash));
		assertThat(orderAfterCheckGathered.getPaid(), hasEntry("CHECK", 0.00));
		assertThat(orderAfterCheckGathered.getGathered(),
				anyOf(not(hasKey("CASH")), hasEntry("CASH", 0.00)));
		assertThat(orderAfterCheckGathered.getGathered(),
				hasEntry("CHECK", paidByCheck));
	}

	private String makePaymentByCheckWith(final String trackingId,
			final double paidByCheck, final String receiptNo) {
		return makePaymentWith(trackingId, paidByCheck, Payment.By.CHECK,
				receiptNo);
	}

	private String makePaymentWith(final String trackingId,
			final double paidByCheck, final Payment.By by,
			final String receiptNo) {
		return commandGateway.sendAndWait(new MakePaymentCommand(trackingId,
				paidByCheck, by.name(), receiptNo));
	}

	private String makePaymentByCashWith(final String trackingId,
			final double paidByCash, final String receiptNo) {
		return makePaymentWith(trackingId, paidByCash, Payment.By.CASH,
				receiptNo);
	}

	private String placeOrderWith(final double total) {
		return commandGateway.sendAndWait(new PlaceOrderCommand(total));
	}

	private ReceiptView findReceiptBy(final String receiptNo) {
		return receipts.findBy(receiptNo);
	}

}
