package com.github.hippoom.ramblings.ordercqrs.mail;

public interface MailSender {

	void sendBalancedNotification(String trackingId);

}
