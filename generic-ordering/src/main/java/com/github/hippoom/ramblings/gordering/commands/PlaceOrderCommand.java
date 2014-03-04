package com.github.hippoom.ramblings.gordering.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PlaceOrderCommand {
	private final String trackingId;
	private final String memberId;
	private final List<String> reservationSpecifications = new ArrayList<String>();

	public PlaceOrderCommand(String trackingId, String memberId,
			String... reservationSpecifications) {
		this.trackingId = trackingId;
		this.memberId = memberId;
		for (String rs : reservationSpecifications) {
			this.reservationSpecifications.add(rs);
		}
	}
}
