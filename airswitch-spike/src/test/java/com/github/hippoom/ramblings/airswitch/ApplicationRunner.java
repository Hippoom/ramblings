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
	public void createAirTicket() throws Throwable {
		final Long ticketId = 1L;
		final Map<Integer, Double> fares = new HashMap<Integer, Double>();
		fares.put(1, 10.00);
		fares.put(2, 20.00);

		System.err.println("<<=======Create AirTicket======>>");

		commandGateway.send(new CreateAirTicketCommand(ticketId, 2L, fares));

		// demandFor(ticketId);

		// cancelFor(ticketId);

		query(ticketId);
	}

	private void query(final Long ticketId) {
		System.err.println("<<=======Query===============>>");

		System.err.println("Current AirTicket:"
				+ entityManager.find(AirTicketDetail.class, ticketId));

		System.err
				.println("Current AirTicketItem:"
						+ entityManager
								.createQuery(
										"select item from AirTicketItemDetail item where item.ticket_id = :ticketId",
										AirTicketItemDetail.class)
								.setParameter("ticketId", ticketId)
								.getResultList());
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
