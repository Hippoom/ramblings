package com.github.hippoom.ramblings.gordering.events;

import lombok.Getter;

@Getter
public class ReservationSpecificationRequestedEvent {
	private final String trackingId;
	private final int seq;
	private final String specification;

	public ReservationSpecificationRequestedEvent(String trackingId,
			String specification) {
		this(trackingId, 1, specification);
	}

	public ReservationSpecificationRequestedEvent(String trackingId, int seq,
			String specification) {
		this.trackingId = trackingId;
		this.seq = seq;
		this.specification = specification;
	}
}
