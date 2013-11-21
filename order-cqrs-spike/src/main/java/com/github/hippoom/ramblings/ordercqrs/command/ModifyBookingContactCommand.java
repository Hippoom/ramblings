package com.github.hippoom.ramblings.ordercqrs.command;

import lombok.Getter;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * 
 * <pre>
 * Modify the booking contact of given order due to 
 * 
 * 1.Incorrect information
 * 2.The current one is not available anymore
 * 
 * </pre>
 */
@Getter
public class ModifyBookingContactCommand {
	/**
	 * identifier of order
	 */
	@TargetAggregateIdentifier
	private final String trackingId;
	/**
	 * name of the booking contact
	 */
	private final String bookingContact;

	/**
	 * 
	 * @param trackingId
	 *            as identifier of order
	 * @param bookingContact
	 *            as name of the booking contact
	 */
	public ModifyBookingContactCommand(String trackingId, String bookingContact) {
		this.trackingId = trackingId;
		this.bookingContact = bookingContact;
	}
}
