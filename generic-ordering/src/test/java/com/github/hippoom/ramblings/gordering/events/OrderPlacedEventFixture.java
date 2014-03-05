package com.github.hippoom.ramblings.gordering.events;

import com.github.hippoom.ramblings.gordering.domain.Order;

public class OrderPlacedEventFixture {

	private String trackingId;
	private String memberId = "a vip member";
	private Order.Status status = Order.Status.FULLFILLING;

	public OrderPlacedEventFixture(String trackingId) {
		this.trackingId = trackingId;
	}

	public OrderPlacedEvent build() {
		return new OrderPlacedEvent(trackingId, memberId, status.name());
	}

}
