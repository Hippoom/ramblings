package com.github.hippoom.ramblings.hotelcatalog;

import java.math.BigDecimal;

public class Rate {

	private final double amountBeforeTax;
	private final DateRange dateRange;

	public Rate(int amountBeforeTax, String date) {
		this(amountBeforeTax, date, date);
	}

	public Rate(int amountBeforeTax, String start, String end) {
		this.amountBeforeTax = amountBeforeTax;
		this.dateRange = DateRange.of(start, end);
	}

	public BigDecimal amountBeforeTax() {
		return BigDecimal.valueOf(amountBeforeTax);
	}

	public DateRange dateRange() {
		return dateRange;
	}

}
