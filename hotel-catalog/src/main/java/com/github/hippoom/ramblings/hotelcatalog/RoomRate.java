package com.github.hippoom.ramblings.hotelcatalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomRate {
	private final String roomTypeCode;
	private final String ratePlanCode;
	private final List<Rate> rates;

	public RoomRate(String roomTypeCode, String ratePlanCode, Rate... rates) {
		this.roomTypeCode = roomTypeCode;
		this.ratePlanCode = ratePlanCode;
		final List<Rate> rs = new ArrayList<Rate>();
		for (Rate rate : rates) {
			rs.add(rate);
		}
		this.rates = rs;
	}

	public double averageAmountBeforeTax() {
		int dates = 0;
		BigDecimal total = BigDecimal.ZERO;
		for (Rate rate : rates()) {
			++dates;
			total = total.add(rate.amountBeforeTax());
		}
		return total.divide(BigDecimal.valueOf(dates)).doubleValue();
	}

	private List<Rate> rates() {
		return rates;
	}

}
