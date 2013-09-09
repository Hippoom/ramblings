package com.github.hippoom.ramblings.credit.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class CreditEntryUnitTests {

	private FixtureConfiguration<CreditEntry> fixture;

	@Before
	public void setUp() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(CreditEntry.class);
	}

	@Test
	public void creditEntryCreated() throws Throwable {
		final Long entryId = 1L;
		final int amount = 100;

		fixture.given().when(new CreateCreditEntryCommand(entryId, amount))
				.expectEvents(new CreditEntryCreatedEvent(entryId, amount));

		//final CreditEntry entry = fixture.getRepository().load(entryId);

		//assertThat(entry.isNew(), is(true));
	}

	@Test
	public void creditEntryMadeEffective() throws Throwable {
		final Long entryId = 1L;
		final int amount = 100;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period

		fixture.given(new CreditEntryCreatedEvent(entryId, amount))
				.when(new MakeCreditEntryEffectiveCommand(entryId, start, end))
				.expectEvents(
						new CreditEntryMadeEffectiveEvent(entryId, start, end));

	}

	private Date nov(int year, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.NOVEMBER, day);
		return c.getTime();
	}

}
