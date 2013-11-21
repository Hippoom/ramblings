package com.github.hippoom.ramblings.bar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FlightLineVacations {
	private Map<String, Map<String, Set<String>>> flightVacations = new HashMap<String, Map<String, Set<String>>>();

	public Set<String> get(String flightNumber, String date) {
		Set<String> set = getVacationAndDates(flightNumber).get(date);
		if (set == null) {
			getVacationAndDates(flightNumber).put(date, new HashSet<String>());
		}
		return getVacationAndDates(flightNumber).get(date);
	}

	private Map<String, Set<String>> getVacationAndDates(String flightNumber) {
		Map<String, Set<String>> map = this.flightVacations.get(flightNumber);
		if (map == null) {
			flightVacations.put(flightNumber,
					new HashMap<String, Set<String>>());
		}
		return this.flightVacations.get(flightNumber);
	}
}