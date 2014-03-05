package com.github.hippoom.ramblings.gordering.domain;

import java.util.Hashtable;
import java.util.Map;

import lombok.Setter;

import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hippoom.ramblings.booking.events.ReservationSpecificationHandledEvent;
import com.github.hippoom.ramblings.gordering.commands.OrderingService;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderAsWaitPaymentCommand;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderLineAmountCommand;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.gordering.events.ReservationSpecificationRequestedEvent;

public class OrderFullfillingSaga extends AbstractAnnotatedSaga {

	private static final long serialVersionUID = 1L;
	@Autowired
	@Setter
	private transient OrderingService orderingService;
	private String trackingId;
	private Map<Integer, Boolean> specHandled = new Hashtable<Integer, Boolean>();

	@StartSaga
	@SagaEventHandler(associationProperty = "trackingId")
	public void on(OrderPlacedEvent event) {
		this.trackingId = event.getTrackingId();
	}

	@SagaEventHandler(associationProperty = "trackingId")
	public void on(ReservationSpecificationRequestedEvent event) {
		specHandled.put(event.getSeq(), false);
	}

	@SagaEventHandler(associationProperty = "trackingId")
	public void on(ReservationSpecificationHandledEvent event) {
		specHandled.put(event.getSeq(), true);
		orderingService.send(new UpdateOrderLineAmountCommand(trackingId, event
				.getSeq(), event.getTotalAmount()));
		if (isAllSpecsHandled()) {
			orderingService
					.send(new UpdateOrderAsWaitPaymentCommand(trackingId));
			end();
		}
	}

	private boolean isAllSpecsHandled() {
		for (Boolean handled : specHandled.values()) {
			if (!handled) {
				return false;
			}
		}
		return true;
	}
}
