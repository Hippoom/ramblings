package com.github.hippoom.ramblings.billing.query;

public interface ReceiptViewStore {

	void addAmount(String receiptNo, double amount);

	ReceiptView findBy(String receiptNo);

}
