package com.github.hippoom.ramblings.bar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FlightPrices {
	private Map<String, Map<String, BigDecimal>> data = new HashMap<String, Map<String, BigDecimal>>();

	public Map<String, BigDecimal> get(String flightNumber) {
		if (this.data.get(flightNumber) == null) {
			this.data.put(flightNumber, new HashMap<String, BigDecimal>());
		}
		return this.data.get(flightNumber);
	}
}
