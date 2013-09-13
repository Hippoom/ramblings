package com.github.hippoom.ramblings.airswitch.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.airswitch.AirTicketCanceledEvent;
import com.github.hippoom.ramblings.airswitch.AirTicketCreatedEvent;
import com.github.hippoom.ramblings.airswitch.AirTicketDemandedEvent;

@Transactional
public class AirTicketDetailSynchronizer {
	@PersistenceContext
	private EntityManager entityManager;

	@EventHandler
	public void on(AirTicketCreatedEvent event) {

		final AirTicketDetail ticket = new AirTicketDetail();
		ticket.id = event.getTicketId();
		ticket.reservation_id = event.getReservationId();
		ticket.status = event.getStatus().name();
		ticket.total_amount = event.getTotalAmount();

		entityManager.persist(ticket);
		
		//throw new RuntimeException("hahaha");
	}

	@EventHandler
	public void on(AirTicketDemandedEvent event) {

		final AirTicketDetail ticket = findBy(event);

		ticket.status = event.getStatus().name();
		ticket.number = event.getNumber();

		entityManager.persist(ticket);
	}

	private AirTicketDetail findBy(AirTicketDemandedEvent event) {
		final AirTicketDetail ticket = entityManager.find(
				AirTicketDetail.class, event.getTicketId());
		return ticket;
	}

	@EventHandler
	public void on(AirTicketCanceledEvent event) {

		final AirTicketDetail ticket = entityManager.find(
				AirTicketDetail.class, event.getTicketId());

		ticket.status = event.getStatus().name();
		ticket.total_amount = event.getPenalty();

		entityManager.persist(ticket);
	}
}
