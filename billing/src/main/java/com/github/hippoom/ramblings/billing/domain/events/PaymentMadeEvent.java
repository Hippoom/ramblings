package com.github.hippoom.ramblings.billing.domain.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PaymentMadeEvent {
	private final String sequence;
	private final String orderId;
	private final double amount;
	private final String by;
	private final String receiptNo;

	public PaymentMadeEvent(String sequence, String orderId, double amount,
			String by, String receiptNo) {
		this.sequence = sequence;
		this.orderId = orderId;
		this.amount = amount;
		this.by = by;
		this.receiptNo = receiptNo;
	}

}
