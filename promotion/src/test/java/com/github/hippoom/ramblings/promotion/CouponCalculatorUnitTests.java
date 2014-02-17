package com.github.hippoom.ramblings.promotion;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CouponCalculatorUnitTests {

	private CouponCalculator calc = new CouponCalculator();

	@Before
	public void configure() {
		calc.addCaculatorInstance(new CouponCaculatorInstance(
				"100OffForEvery300Spent")
				.scriptIs(
						"return (${totalAmount} >= ${threshold}? ${totalAmount} - (${totalAmount} / ${threshold}).setScale(0, BigDecimal.ROUND_FLOOR) * ${off}: ${totalAmount})")
				.addParam("off", 100).addParam("threshold", 300));
		calc.addCaculatorInstance(new CouponCaculatorInstance(
				"100OffFor300Spent")
				.scriptIs(
						"return (${totalAmount} >= ${threshold}? ${totalAmount} - ${off}: ${totalAmount}) ")
				.addParam("off", 100).addParam("threshold", 300));
	}

	@Test
	public void gets100OffForEvery300Spent() throws Throwable {

		assertThat(calc.totalAmountAfter("100OffForEvery300Spent", 1000),
				equalTo(700.0));
	}

	@Test
	public void gets100OffForEvery300Spent_belowThreshold() throws Throwable {

		assertThat(calc.totalAmountAfter("100OffForEvery300Spent", 299),
				equalTo(299.0));
	}

	@Test
	public void gets100OffFor300Spent() throws Throwable {

		assertThat(calc.totalAmountAfter("100OffFor300Spent", 1000),
				equalTo(900.0));
	}

	@Test
	public void gets100OffFor300Spent_belowThreshold() throws Throwable {

		assertThat(calc.totalAmountAfter("100OffFor300Spent", 299),
				equalTo(299.0));
	}

}
