package com.github.hippoom.ramblings.gordering.test;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.github.hippoom.ramblings.gordering.events.OrderFullfilledEvent;
import com.github.hippoom.ramblings.gordering.events.OrderLineAmountUpdatedEvent;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.gordering.query.OrderView;

public class InMemoryOrderQueryService {
	private Map<String, OrderView> orders = new HashMap<String, OrderView>();

	@EventHandler
	public void on(OrderPlacedEvent event) {
		final OrderView order = new OrderView();
		order.setTrackingId(event.getTrackingId());
		order.setMemberId(event.getMemberId());
		order.setStatus(event.getStatus());
		orders.put(event.getTrackingId(), order);
	}

	@EventHandler
	public void on(OrderLineAmountUpdatedEvent event) {
		final OrderView order = trackingIdAs(event.getTrackingId());
		order.setTotalAmount(order.getTotalAmount() + event.getAmount());
		// TODO cannot handle orderLineAmount re-update for now
	}

	@EventHandler
	public void on(OrderFullfilledEvent event) {
		final OrderView order = trackingIdAs(event.getTrackingId());
		order.setStatus(event.getStatus());
	}

	public OrderView trackingIdAs(String trackingId) {
		return orders.get(trackingId);
	}
}
