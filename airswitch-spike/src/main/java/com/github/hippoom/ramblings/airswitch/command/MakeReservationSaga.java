package com.github.hippoom.ramblings.airswitch.command;

import lombok.Setter;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;

import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservationCreatedEvent;
import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketFactory;
import com.github.hippoom.ramblings.airswitch.command.ticket.CreateAirTicketCommand;

@SuppressWarnings("serial")
public class MakeReservationSaga extends AbstractAnnotatedSaga {
	@Setter
	private transient CommandGateway commandGateway;
	@Setter
	private transient AirTicketFactory ticketFactory;

	@StartSaga
	@SagaEventHandler(associationProperty = "reservationId")
	public void createTickets(AirReservationCreatedEvent event) {

		final Long firstTktId = ticketFactory.nextIdentifier();
		final Long secondTktId = ticketFactory.nextIdentifier();
		associateWith("ticketId", firstTktId);
		associateWith("ticketId", secondTktId);

		commandGateway.send(new CreateAirTicketCommand(firstTktId, event
				.getReservationId(), null));
		commandGateway.send(new CreateAirTicketCommand(secondTktId, event
				.getReservationId(), null));

	}
}
