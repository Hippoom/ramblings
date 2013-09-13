package com.github.hippoom.ramblings.airswitch;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hippoom.ramblings.airswitch.command.reservation.CreateAirReservationCommand;
import com.github.hippoom.ramblings.airswitch.command.ticket.CancelAirTicketCommand;
import com.github.hippoom.ramblings.airswitch.command.ticket.CreateAirTicketCommand;
import com.github.hippoom.ramblings.airswitch.command.ticket.DemandAirTicketCommand;
import com.github.hippoom.ramblings.airswitch.query.AirReservationDetail;
import com.github.hippoom.ramblings.airswitch.query.AirTicketDetail;
import com.github.hippoom.ramblings.airswitch.query.AirTicketItemDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:command.xml",
		"classpath:query.xml", "classpath:persistence.xml" })
public class ApplicationRunner {
	@Autowired
	private CommandGateway commandGateway;
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void makeReservation() throws Throwable {

		final Long reservationId = 2L;

		final Long ticketId1 = 1L;
		final Long ticketId2 = 2L;

		System.err.println("<<=======Create AirReservation======>>");

		commandGateway.sendAndWait(new CreateAirReservationCommand(
				reservationId, "John Doe", "SHA-->PEK"));

		createAirTicket(ticketId1, reservationId);
		createAirTicket(ticketId2, reservationId);

		// demandFor(ticketId);

		// cancelFor(ticketId);

		queryAirReservationBy(reservationId);
		// queryAirTicketBy(ticketId1);
	}

	private void createAirTicket(final Long ticketId, final Long reservationId) {
		System.err.println("<<=======Create AirTicket======>>");
		final Map<Integer, Double> fares = new HashMap<Integer, Double>();
		fares.put(1, 10.00);
		fares.put(2, 20.00);

		commandGateway.sendAndWait(new CreateAirTicketCommand(ticketId,
				reservationId, fares));
	}

	private void queryAirReservationBy(final Long id) {
		System.err.println("<<=======Query AirReservation===============>>");

		System.err.println("Current AirReservation:"
				+ entityManager.find(AirReservationDetail.class, id));

	}

	private void queryAirTicketBy(final Long id) {
		System.err.println("<<=======Query AirTicket===============>>");

		System.err.println("Current AirTicket:"
				+ entityManager.find(AirTicketDetail.class, id));

		System.err
				.println("Current AirTicketItem:"
						+ entityManager
								.createQuery(
										"select item from AirTicketItemDetail item where item.ticket_id = :ticketId",
										AirTicketItemDetail.class)
								.setParameter("ticketId", id).getResultList());
	}

	private void cancelFor(final Long ticketId) {
		System.err.println("<<=======Cancel AirTicket===============>>");

		commandGateway.send(new CancelAirTicketCommand(ticketId, 5.00));
	}

	private void demandFor(final Long ticketId) {
		System.err.println("<<=======Demand AirTicket===============>>");

		commandGateway.send(new DemandAirTicketCommand(ticketId, "a pnr no"));
	}
}
