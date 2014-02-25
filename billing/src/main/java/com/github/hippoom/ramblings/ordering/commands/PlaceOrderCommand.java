package com.github.hippoom.ramblings.ordering.commands;

import lombok.Getter;

@Getter
public class PlaceOrderCommand {
	private final double total;

	public PlaceOrderCommand(double total) {
		this.total = total;
	}
}
