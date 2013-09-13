package com.github.hippoom.ramblings.airswitch;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

public class AirTicketUnitTests {
	private FixtureConfiguration<AirTicket> fixture = Fixtures
			.newGivenWhenThenFixture(AirTicket.class);

	@Test
	public void airTicketCreated() throws Throwable {
		final Long id = 1L;
		final Long reservationId = 2L;
		final Map<Integer, Double> fares = new HashMap<Integer, Double>();
		fares.put(1, 100.00);
		fares.put(2, 200.00);

		fixture.given()
				.when(new CreateAirTicketCommand(id, reservationId, fares))
				.expectEvents(
						new AirTicketCreatedEvent(id, reservationId,
								AirTicket.Status.NEW, 300),
						new AirTicketItemCreatedEvent(id, 1, 100.00),
						new AirTicketItemCreatedEvent(id, 2, 200.00));

	}

	@Test
	public void airTicketDemanded() throws Throwable {
		final Long id = 1L;
		final Long reservationId = 2L;
		final Map<Integer, Double> fares = new HashMap<Integer, Double>();
		fares.put(1, 100.00);
		fares.put(2, 200.00);
		final String number = "123";

		fixture.given(
				new AirTicketCreatedEvent(id, reservationId,
						AirTicket.Status.NEW, 300),
				new AirTicketItemCreatedEvent(id, 1, 100.00),
				new AirTicketItemCreatedEvent(id, 2, 200.00))
				.when(new DemandAirTicketCommand(id, number))
				.expectEvents(
						new AirTicketDemandedEvent(id, number,
								AirTicket.Status.DEMANDED));

	}

	@Test
	public void airTicketCanceled() throws Throwable {
		final Long id = 1L;
		final Long reservationId = 2L;
		final Map<Integer, Double> fares = new HashMap<Integer, Double>();
		fares.put(1, 100.00);
		fares.put(2, 200.00);
		final String number = "123";
		final double penalty = 30.00;

		fixture.given(
				new AirTicketCreatedEvent(id, reservationId,
						AirTicket.Status.NEW, 300),
				new AirTicketItemCreatedEvent(id, 1, 100.00),
				new AirTicketItemCreatedEvent(id, 2, 200.00),
				new AirTicketDemandedEvent(id, number,
						AirTicket.Status.DEMANDED))
				.when(new CancelAirTicketCommand(id, penalty))
				.expectEvents(
						new AirTicketCanceledEvent(id, penalty,
								AirTicket.Status.CANCELED));

	}
}
