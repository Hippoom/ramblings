package com.github.hippoom.ramblings.billing.query;

import lombok.Getter;

@Getter
public class ReceiptView {
	private String receiptNo;
	private double amount;

	public ReceiptView(String receiptNo, double amount) {
		this.receiptNo = receiptNo;
		this.amount = amount;
	}
}
