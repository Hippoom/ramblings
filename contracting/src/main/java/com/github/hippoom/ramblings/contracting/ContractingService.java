package com.github.hippoom.ramblings.contracting;

import java.util.HashMap;
import java.util.Map;

public class ContractingService {

	private Map<String, String> predifinedPlaceHolders = new HashMap<String, String>();

	public void add(String key, String value) {
		this.predifinedPlaceHolders.put(key, value);
	}

	public ContractResolver resolve(String origin,
			Map<String, String> productSpecificPlaceHolders) {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.putAll(this.predifinedPlaceHolders);
		placeHolders.putAll(productSpecificPlaceHolders);
		return new ContractResolver(origin, placeHolders);
	}

}
