package com.github.hippoom.ramblings.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CouponCalculator {

	private double totalAmountAfter;
	private double totalOff;

	public CouponCalculator(double threshold, double off,
			double totalAmountBefore) {
		this.totalOff = BigDecimal.valueOf(totalAmountBefore)
				.divide(BigDecimal.valueOf(threshold), 0, RoundingMode.FLOOR)
				.multiply(BigDecimal.valueOf(off)).doubleValue();
		this.totalAmountAfter = totalAmountBefore - totalOff;
	}

	public CouponCalculator(double thresholdPerSpent, double thresholdTotal,
			double off, double totalAmountBefore) {
		this.totalOff = thresholdTotal < totalAmountBefore ? off : 0;
		this.totalAmountAfter = totalAmountBefore - totalOff;
	}

	public double totalOff() {
		return totalOff;
	}

	public double totalAmountAfter() {
		return totalAmountAfter;
	}
}
