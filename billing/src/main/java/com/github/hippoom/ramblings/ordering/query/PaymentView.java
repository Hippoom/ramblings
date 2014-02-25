package com.github.hippoom.ramblings.ordering.query;

import lombok.Getter;

@Getter
public class PaymentView {
	private String sequence;
	private String orderId;
	private double amount;
	private String by;

	public PaymentView(String sequence, String orderId, double amount, String by) {
		this.sequence = sequence;
		this.orderId = orderId;
		this.amount = amount;
		this.by = by;
	}
}
