package com.github.hippoom.ramblings.promotion;

import java.util.HashMap;
import java.util.Map;

public class CouponCalculator {

	private Map<String, CouponCaculatorInstance> instances = new HashMap<String, CouponCaculatorInstance>();

	public double totalAmountAfter(String identifier, double totalAmountBefore) {
		return instances.get(identifier).calculateWith(totalAmountBefore);
	}

	public void addCaculatorInstance(
			CouponCaculatorInstance couponCaculatorInstance) {
		instances.put(couponCaculatorInstance.identifier(),
				couponCaculatorInstance);
	}
}
