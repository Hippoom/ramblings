package com.github.hippoom.ramblings.billing.domain.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PaymentGatheredEvent {
	private final String sequence;

	public PaymentGatheredEvent(String sequence) {
		this.sequence = sequence;
	}

}
