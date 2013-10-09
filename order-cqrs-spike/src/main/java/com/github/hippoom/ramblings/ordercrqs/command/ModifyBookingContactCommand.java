package com.github.hippoom.ramblings.ordercrqs.command;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ModifyBookingContactCommand {
	@Getter
	@TargetAggregateIdentifier
	private final String trackingId;
	@Getter
	private final String bookingContact;

	public ModifyBookingContactCommand(String trackingId, String bookingContact) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
	}
}
