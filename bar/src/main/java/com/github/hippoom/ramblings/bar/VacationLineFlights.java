package com.github.hippoom.ramblings.bar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VacationLineFlights {
	private Map<String, Map<String, Set<String>>> data = new HashMap<String, Map<String, Set<String>>>();

	public Set<String> get(String vacation, String date) {
		Set<String> set = getFlightAndDates(vacation).get(date);
		if (set == null) {
			getFlightAndDates(vacation).put(date, new HashSet<String>());
		}
		return getFlightAndDates(vacation).get(date);
	}

	private Map<String, Set<String>> getFlightAndDates(String vacation) {
		Map<String, Set<String>> map = this.data.get(vacation);
		if (map == null) {
			data.put(vacation, new HashMap<String, Set<String>>());
		}
		return this.data.get(vacation);
	}
}