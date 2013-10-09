package com.github.hippoom.ramblings.ordercqrs.saga;

import com.github.hippoom.ramblings.ordercqrs.mail.MailSender;

public class MailSenderStub implements MailSender {
	private String trackingId;

	public String getTrackingId() {
		return trackingId;
	}

	@Override
	public void sendBalancedNotification(String trackingId) {
		this.trackingId = trackingId;
	}

}
