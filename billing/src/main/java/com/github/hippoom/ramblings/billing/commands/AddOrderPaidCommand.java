package com.github.hippoom.ramblings.billing.commands;

import lombok.Getter;

@Getter
public class AddOrderPaidCommand {
	private String orderId;
	private double augend;

	public AddOrderPaidCommand(String orderId, double augend) {
		this.orderId = orderId;
		this.augend = augend;
	}
}
