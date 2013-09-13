package com.github.hippoom.ramblings.airswitch.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.airswitch.AirTicketItemCreatedEvent;

@Transactional
public class AirTicketItemDetailSynchronizer {
	@PersistenceContext
	private EntityManager entityManager;

	@EventHandler
	public void on(AirTicketItemCreatedEvent event) {

		final AirTicketItemDetail ticket = new AirTicketItemDetail();
		ticket.ticket_id = event.getTicketId();
		ticket.rph = event.getRph();
		ticket.fare = event.getFare();

		entityManager.persist(ticket);

	}

}
