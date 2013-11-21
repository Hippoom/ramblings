package com.github.hippoom.ramblings.ordercqrs.query.daily;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.ordercqrs.event.OrderPlacedEvent;

@Transactional
public class DailyReadModelSynchronizer {
	@PersistenceContext
	private EntityManager em;

	@EventHandler
	public void on(OrderPlacedEvent event) {
		final DailyOrderCountEntryReadModel order = new DailyOrderCountEntryReadModel();
		order.setTrackingId(event.getTrackingId());
		order.setToday(new SimpleDateFormat("yyyy-MM-dd").format(event
				.getWhenPlaced()));
		em.persist(order);
	}

}
