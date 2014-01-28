package com.github.hippoom.ramblings.billing.commands;

import lombok.Getter;

@Getter
public class MakePaymentCommand {
	private final String orderId;
	private final double amount;
	private final String by;
	private final String receiptNo;

	public MakePaymentCommand(String orderId, double amount, String by,
			String receiptNo) {
		this.orderId = orderId;
		this.amount = amount;
		this.by = by;
		this.receiptNo = receiptNo;
	}
}
