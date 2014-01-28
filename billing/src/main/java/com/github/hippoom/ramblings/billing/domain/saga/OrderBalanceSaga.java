package com.github.hippoom.ramblings.billing.domain.saga;

import lombok.Setter;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;

import com.github.hippoom.ramblings.billing.commands.AddOrderPaidCommand;
import com.github.hippoom.ramblings.billing.domain.events.PaymentMadeEvent;

public class OrderBalanceSaga extends AbstractAnnotatedSaga {

	private static final long serialVersionUID = 1L;
	@Setter
	private transient CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void on(PaymentMadeEvent event) {
		commandGateway.sendAndWait(new AddOrderPaidCommand(event.getOrderId(),
				event.getAmount()));
		end();
	}

}
