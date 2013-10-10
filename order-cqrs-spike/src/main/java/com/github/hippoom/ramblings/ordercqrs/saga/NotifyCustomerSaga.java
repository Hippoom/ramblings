package com.github.hippoom.ramblings.ordercqrs.saga;

import lombok.Setter;

import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hippoom.ramblings.ordercqrs.domain.BalanceStatus;
import com.github.hippoom.ramblings.ordercqrs.domain.CustomerNotifier;
import com.github.hippoom.ramblings.ordercqrs.event.PaymentMadeEvent;

@SuppressWarnings("serial")
public class NotifyCustomerSaga extends AbstractAnnotatedSaga {
	@Autowired
	@Setter
	private CustomerNotifier mailSender;

	@StartSaga
	@SagaEventHandler(associationProperty = "trackingId")
	public void sendsEmailIfBalancedOn(PaymentMadeEvent event) {
		if (BalanceStatus.BALANCED.getValue().equals(event.getBalanceStatus())) {
			mailSender.notifyOrderBalanced(event.getTrackingId());
			end();
		}

	}
}
