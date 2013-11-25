package com.github.hippoom.ramblings.hotelcatalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
		int dates = dateRange().days();
		BigDecimal total = BigDecimal.ZERO;
		for (Rate rate : rates()) {
			total = total.add(rate.amountBeforeTax());
		}
		return total.divide(BigDecimal.valueOf(dates)).doubleValue();
	}

	private List<Rate> rates() {
		return rates;
	}

	public DateRange dateRange() {
		List<DateRange> dateRanges = new ArrayList<DateRange>();
		for (Rate rate : rates()) {
			dateRanges.add(rate.dateRange());
		}
		if (dateRanges.size() == 1) {
			return dateRanges.get(0);
		}
		Date start = null;
		Date end = null;
		start = dateRanges.get(0).start();
		end = dateRanges.get(0).end();
		for (int i = 1; i < dateRanges.size(); i++) {
			DateRange current = dateRanges.get(i);
			if (start.after(current.start())) {
				start = current.start();
			}
			if (end.before(current.end())) {
				end = current.end();
			}
		}
		return DateRange.of(start, end);
	}

}
