package com.github.hippoom.ramblings.gordering.commands;

import lombok.Getter;

@Getter
public class PlaceOrderCommand {
	private final String trackingId;
	private final String memberId;

	public PlaceOrderCommand(String trackingId, String memberId) {
		this.trackingId = trackingId;
		this.memberId = memberId;
	}

}
