package com.github.hippoom.ramblings.promotion;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CouponUnitTests {

	@Test
	public void gets100OffForEvery300Spent() throws Throwable {
		CouponCalculator calc = new CouponCalculator(300, 100, 1000);

		assertThat(calc.totalAmountAfter(), equalTo(700.0));
		assertThat(calc.totalOff(), equalTo(300.0));
	}

	@Test
	public void gets100OffForEvery300Spent_belowThreshold() throws Throwable {
		CouponCalculator calc = new CouponCalculator(300, 100, 299);

		assertThat(calc.totalAmountAfter(), equalTo(299.0));
		assertThat(calc.totalOff(), equalTo(0.0));
	}

	@Test
	public void gets100OffFor300Spent() throws Throwable {
		CouponCalculator calc = new CouponCalculator(0, 300, 100, 1000);

		assertThat(calc.totalAmountAfter(), equalTo(900.0));
		assertThat(calc.totalOff(), equalTo(100.0));
	}

	@Test
	public void gets100OffFor300Spent_belowThreshold() throws Throwable {
		CouponCalculator calc = new CouponCalculator(0, 300, 100, 299);

		assertThat(calc.totalAmountAfter(), equalTo(299.0));
		assertThat(calc.totalOff(), equalTo(0.0));
	}
}
