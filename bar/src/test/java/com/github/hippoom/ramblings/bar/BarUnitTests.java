package com.github.hippoom.ramblings.bar;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class BarUnitTests {
	private Bar bar = new Bar();

	@Test
	public void show() throws Throwable {

		bar.assignFlightNumberToVacation("MU8801", "10-02", "V001");
		bar.assignFlightNumberToVacation("MU8802", "10-02", "V001");

		bar.updateFlightPrice("MU8801", "10-02", BigDecimal.valueOf(1200));
		bar.updateFlightPrice("MU8802", "10-02", BigDecimal.valueOf(1300));

		System.err.println(bar);

		assertThat(bar.getVacationFlightBars().get("V001").get("10-02"),
				equalTo(BigDecimal.valueOf(1200)));
		assertThat(bar.getVacationBar("V001", "10-02"),
				equalTo(BigDecimal.valueOf(1200)));

	}

	@Test
	public void show1() throws Throwable {

		bar.assignFlightNumberToVacation("MU8801", "10-02", "V001");
		bar.assignFlightNumberToVacation("MU8802", "10-02", "V001");

		bar.updateFlightPrice("MU8801", "10-02", BigDecimal.valueOf(1200));
		bar.updateFlightPrice("MU8802", "10-02", BigDecimal.valueOf(1300));
		bar.updateFlightPrice("MU8802", "10-02", BigDecimal.valueOf(600));

		System.err.println(bar);

		assertThat(bar.getVacationFlightBars().get("V001").get("10-02"),
				equalTo(BigDecimal.valueOf(600)));
		assertThat(bar.getVacationBar("V001", "10-02"),
				equalTo(BigDecimal.valueOf(600)));

	}

	@Test
	public void show2() throws Throwable {

		bar.assignFlightNumberToVacation("MU8801", "10-02", "V001");
		bar.assignFlightNumberToVacation("MU8802", "10-02", "V001");

		bar.updateFlightPrice("MU8801", "10-02", BigDecimal.valueOf(1200));
		bar.updateFlightPrice("MU8802", "10-02", BigDecimal.valueOf(1300));
		bar.deprecateFlightPrice("MU8801", "10-02");

		System.err.println(bar);

		assertThat(bar.getFlightPrices().get("MU8801").get("10-02"),
				equalTo(BigDecimal.ZERO));
		assertThat(bar.getVacationFlightBars().get("V001").get("10-02"),
				equalTo(BigDecimal.valueOf(1300)));
		assertThat(bar.getVacationBar("V001", "10-02"),
				equalTo(BigDecimal.valueOf(1300)));

	}
}
