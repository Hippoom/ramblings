package com.github.hippoom.ramblings.ordercrqs.domain;

import static com.github.hippoom.ramblings.ordercrqs.domain.BalanceStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class BalanceUnitTests {

	@Test
	public void returnsBalancedWhenTotalAmountEqualsToPaid() throws Throwable {
		final Balance balance = Balance.reconsititue(BigDecimal.ONE,
				BigDecimal.ONE);

		assertThat(balance.getStatus(), equalTo(BALANCED));
	}

	@Test
	public void returnsBalancedWhenTotalAmountDoesNotEqualToPaid()
			throws Throwable {
		final Balance balance = Balance.reconsititue(BigDecimal.ONE,
				BigDecimal.ZERO);

		assertThat(balance.getStatus(), equalTo(UNBALANCED));
	}
}
