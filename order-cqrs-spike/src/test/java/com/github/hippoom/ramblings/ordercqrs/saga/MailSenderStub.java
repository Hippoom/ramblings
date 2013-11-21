package com.github.hippoom.ramblings.ordercqrs.saga;

import com.github.hippoom.ramblings.ordercqrs.domain.CustomerNotifier;

public class MailSenderStub implements CustomerNotifier {
	private String trackingId;

	public String getTrackingId() {
		return trackingId;
	}

	@Override
	public void notifyOrderBalanced(String trackingId) {
		this.trackingId = trackingId;
	}

}
