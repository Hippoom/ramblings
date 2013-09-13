package com.github.hippoom.ramblings.airswitch.command;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hippoom.ramblings.airswitch.command.reservation.AirTicketSubtotalSummedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.CalculateTotalOfReservationCommand;
import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketItemsCreatedEvent;

@SuppressWarnings("serial")
public class MakeReservationSaga extends AbstractAnnotatedSaga {
	@Autowired
	@Setter
	private transient CommandGateway commandGateway;

	private Map<Long, Boolean> ticketsToSum = new HashMap<Long, Boolean>();

	@StartSaga
	@SagaEventHandler(associationProperty = "reservationId")
	public void updateReservationTotalAmount(AirTicketItemsCreatedEvent event) {

		ticketsToSum.put(event.getTicketId(), false);

		commandGateway.send(new CalculateTotalOfReservationCommand(event
				.getReservationId(), event.getTicketId(), event.getSubtotal()));

	}

	@SagaEventHandler(associationProperty = "reservationId")
	public void createTickets(AirTicketSubtotalSummedEvent event) {
		ticketsToSum.put(event.getTicketId(), true);

		if (ticketsToSum.containsValue(false)) {
			// wait for other tickets get summed
		} else {
			end();
		}
	}

}
