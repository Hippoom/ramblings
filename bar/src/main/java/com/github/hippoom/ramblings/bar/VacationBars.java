package com.github.hippoom.ramblings.bar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VacationBars {
	private Map<String, Map<String, BigDecimal>> data = new HashMap<String, Map<String, BigDecimal>>();

	public Map<String, BigDecimal> get(String vacation) {
		if (this.data.get(vacation) == null) {
			this.data.put(vacation, new HashMap<String, BigDecimal>());
		}
		return this.data.get(vacation);
	}
}
