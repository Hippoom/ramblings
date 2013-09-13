package com.github.hippoom.ramblings.airswitch;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.hippoom.ramblings.airswitch.command.MakeReservationSaga;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservation;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservationCreatedEvent;
import com.github.hippoom.ramblings.airswitch.command.ticket.AirTicketFactory;
import com.github.hippoom.ramblings.airswitch.command.ticket.CreateAirTicketCommand;

public class MakeReservationSagaUnitTests {
	private AnnotatedSagaTestFixture fixture = new AnnotatedSagaTestFixture(
			MakeReservationSaga.class);
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@Mock
	private AirTicketFactory airTicketFactory;

	@Before
	public void inject() {
		fixture.registerResource(airTicketFactory);
		fixture.registerCommandGateway(CommandGateway.class);
	}

	@Test
	public void createAirTicketsAfterAirReservationCreated() throws Throwable {

		final Long reservationId = 1L;
		final Long firstTicketId = 2L;
		final Map<Integer, Double> faresOfFirstTicket = new HashMap<Integer, Double>();
		faresOfFirstTicket.put(1, 10.00);
		faresOfFirstTicket.put(2, 10.00);

		final Long secondTicketId = 3L;
		final Map<Integer, Double> faresOfSecondTicket = new HashMap<Integer, Double>();
		faresOfSecondTicket.put(1, 10.00);
		faresOfSecondTicket.put(2, 10.00);

		context.checking(new Expectations() {
			{
				oneOf(airTicketFactory).nextIdentifier();
				will(returnValue(firstTicketId));

				oneOf(airTicketFactory).nextIdentifier();
				will(returnValue(secondTicketId));
			}
		});

		fixture.whenPublishingA(
				new AirReservationCreatedEvent(reservationId,
						AirReservation.Status.NEW))
				.expectAssociationWith("ticketId", firstTicketId)
				.expectAssociationWith("ticketId", secondTicketId)
				.expectDispatchedCommandsEqualTo(
						new CreateAirTicketCommand(firstTicketId,
								reservationId, null),
						new CreateAirTicketCommand(secondTicketId,
								reservationId, null));

	}
}
