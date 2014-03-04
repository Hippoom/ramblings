package com.github.hippoom.ramblings.gordering.events;

import lombok.Getter;

@Getter
public class ReservationSpecificationRequestedEvent {
	private final String trackingId;
	private final String specification;

	public ReservationSpecificationRequestedEvent(String trackingId,
			String specification) {
		this.trackingId = trackingId;
		this.specification = specification;
	}
}
