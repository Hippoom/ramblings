package com.github.hippoom.ramblings.credit.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class CreditAccountUnitTests {

	private FixtureConfiguration<CreditAccount> fixture;

	@Before
	public void setUp() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(CreditAccount.class);
	}

	@Test
	public void creditAccountCreated() throws Throwable {
		final Long entryId = 1L;
		final int amount = 100;

		fixture.given().when(new CreateCreditAccountCommand(entryId, amount))
				.expectEvents(new CreditAccountCreatedEvent(entryId, amount));

		//final CreditEntry entry = fixture.getRepository().load(entryId);

		//assertThat(entry.isNew(), is(true));
	}

	@Test
	public void creditAccountMadeEffective() throws Throwable {
		final Long entryId = 1L;
		final int amount = 100;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period

		fixture.given(new CreditAccountCreatedEvent(entryId, amount))
				.when(new MakeCreditAccountEffectiveCommand(entryId, start, end))
				.expectEvents(
						new CreditAccountMadeEffectiveEvent(entryId, start, end));

	}

	private Date nov(int year, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.NOVEMBER, day);
		return c.getTime();
	}

}
