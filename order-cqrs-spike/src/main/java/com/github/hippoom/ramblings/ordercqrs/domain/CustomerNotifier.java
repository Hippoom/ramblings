package com.github.hippoom.ramblings.ordercqrs.domain;

public interface CustomerNotifier {

	void notifyOrderBalanced(String trackingId);

}
