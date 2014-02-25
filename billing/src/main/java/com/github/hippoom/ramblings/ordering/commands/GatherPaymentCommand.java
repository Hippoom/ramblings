package com.github.hippoom.ramblings.ordering.commands;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GatherPaymentCommand {
	private final String orderId;
	private final String sequence;

	public GatherPaymentCommand(String orderId, String sequence) {
		this.orderId = orderId;
		this.sequence = sequence;
	}

}
