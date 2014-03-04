package com.github.hippoom.ramblings.gordering.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.github.hippoom.ramblings.gordering.events.ReservationSpecificationRequestedEvent;

public class InMemoryBookingAdapter {
	private Map<String, List<String>> orders = new HashMap<String, List<String>>();

	@EventHandler
	public void on(ReservationSpecificationRequestedEvent event) {
		List<String> specs = orders.get(event.getTrackingId());
		if (specs != null) {
			specs.add(event.getSpecification());
		} else {
			specs = new ArrayList<String>();
			specs.add(event.getSpecification());
			orders.put(event.getTrackingId(), specs);
		}

	}

	public List<String> get(String trackingId) {
		return orders.get(trackingId);
	}

}
