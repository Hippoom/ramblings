package com.github.hippoom.ramblings.ordercqrs.query.detail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.ordercqrs.event.BalanceUpdatedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.OrderPlacedEvent;

@Transactional
public class DetailReadModelSynchronizer {
	@PersistenceContext
	private EntityManager em;

	@EventHandler
	public void on(OrderPlacedEvent event) {
		final OrderDetailReadModel order = new OrderDetailReadModel();
		order.setTrackingId(event.getTrackingId());
		order.setWhenPlaced(event.getWhenPlaced());
		order.setTotalAmount(event.getTotalAmount().doubleValue());
		order.setTotalPaid(event.getTotalPaid().doubleValue());
		order.setBalanceStatus(event.getBalanceStatus());
		em.persist(order);
	}

	@EventHandler
	public void on(BookingContactUpdatedEvent event) {
		final OrderDetailReadModel order = em.find(OrderDetailReadModel.class,
				event.getTrackingId());
		order.setBookingContactName(event.getBookingContact());

		em.persist(order);
	}

	@EventHandler
	public void on(BalanceUpdatedEvent event) {
		final OrderDetailReadModel order = em.find(OrderDetailReadModel.class,
				event.getTrackingId());
		order.setTotalAmount(event.getTotalAmount().doubleValue());
		order.setTotalPaid(event.getPaid().doubleValue());
		order.setBalanceStatus(event.getBalanceStatus());

		em.persist(order);
	}
}
