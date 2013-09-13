package com.github.hippoom.ramblings.airswitch;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.github.hippoom.ramblings.airswitch.command.MakeReservationSaga;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirTicketSubtotalSummedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.CalculateTotalOfReservationCommand;
import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketItemsCreatedEvent;

public class MakeReservationSagaUnitTests {
	private AnnotatedSagaTestFixture fixture = new AnnotatedSagaTestFixture(
			MakeReservationSaga.class);

	@Before
	public void inject() {
		fixture.registerCommandGateway(CommandGateway.class);
	}

	@Test
	public void updateTotalAmountOfReservationWhenAirTicketItemsCreated()
			throws Throwable {

		final Long reservationId = 1L;
		final Long ticketId = 2L;

		fixture.whenPublishingA(
				new AirTicketItemsCreatedEvent(ticketId, reservationId, 100.00))
				.expectDispatchedCommandsEqualTo(
						new CalculateTotalOfReservationCommand(reservationId,
								ticketId, 100.00));

	}

	@Test
	public void continueProcessGivenAnyTicketSummed() throws Throwable {

		final Long reservationId = 1L;
		final Long ticketId1 = 2L;
		final Long ticketId2 = 3L;

		fixture.givenAPublished(
				new AirTicketItemsCreatedEvent(ticketId1, reservationId, 100.00))
				.andThenAPublished(
						new AirTicketItemsCreatedEvent(ticketId2,
								reservationId, 100.00))
				.whenPublishingA(
						new AirTicketSubtotalSummedEvent(reservationId,
								ticketId1, 100.00, 100.00))
				.expectNoDispatchedCommands().expectActiveSagas(1);

	}

	@Test
	public void endProcessGivenAllTicketsSummed() throws Throwable {

		final Long reservationId = 1L;
		final Long ticketId = 2L;

		fixture.givenAPublished(
				new AirTicketItemsCreatedEvent(ticketId, reservationId, 100.00))
				.whenPublishingA(
						new AirTicketSubtotalSummedEvent(reservationId,
								ticketId, 100.00, 100.00))
				.expectNoDispatchedCommands().expectActiveSagas(0);

	}
}
