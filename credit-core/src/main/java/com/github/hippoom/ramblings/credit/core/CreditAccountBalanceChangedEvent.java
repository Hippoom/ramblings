package com.github.hippoom.ramblings.credit.core;

public class CreditAccountBalanceChangedEvent {
	private final int amount;

	public CreditAccountBalanceChangedEvent(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}
