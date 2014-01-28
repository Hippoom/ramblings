package com.github.hippoom.ramblings.billing.commands;

import lombok.Getter;

@Getter
public class GatherPaymentCommand {
	private final String sequence;

	public GatherPaymentCommand(String sequence) {
		this.sequence = sequence;
	}
}
