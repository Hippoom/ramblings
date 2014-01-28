package com.github.hippoom.ramblings.billing.commands;

import lombok.Getter;

@Getter
public class PlaceOrderCommand {
	private final double total;

	public PlaceOrderCommand(double total) {
		this.total = total;
	}
}
