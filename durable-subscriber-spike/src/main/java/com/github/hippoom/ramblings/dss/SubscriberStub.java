package com.github.hippoom.ramblings.dss;

public class SubscriberStub {
	private String message;

	public void receive(String message) {
		System.err.println("message:" + message);
		this.message = message;
	}

	public String received() {
		System.err.println("received:" + message);
		return message;
	}
}
