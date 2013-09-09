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
		final Long accountId = 1L;
		final int amount = 100;

		fixture.given().when(new CreateCreditAccountCommand(accountId, amount))
				.expectEvents(new CreditAccountCreatedEvent(accountId, amount));

		// final CreditEntry entry = fixture.getRepository().load(entryId);

		// assertThat(entry.isNew(), is(true));
	}

	@Test
	public void creditAccountMadeEffective() throws Throwable {
		final Long accountId = 1L;
		final int amount = 100;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period

		fixture.given(new CreditAccountCreatedEvent(accountId, amount))
				.when(new MakeCreditAccountEffectiveCommand(accountId, start,
						end))
				.expectEvents(new CreditAccountMadeEffectiveEvent(start, end));

	}

	@Test
	public void creditAccountBalanceChangedWhenCreditTransfered()
			throws Throwable {
		final Long accountId = 1L;
		final int amount = 100;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period

		fixture.given(new CreditAccountCreatedEvent(accountId, amount),
				new CreditAccountMadeEffectiveEvent(start, end))
				.when(new TransferCreditCommand(accountId, amount,
						nov(2012, 10)))
				.expectEvents(new CreditAccountBalanceChangedEvent(amount));

	}

	@Test
	public void throwExceptionWhenCreditTransferedGivenAccountIsNotEffective()
			throws Throwable {
		final Long accountId = 1L;
		final int amount = 100;

		fixture.given(new CreditAccountCreatedEvent(accountId, amount))
				.when(new TransferCreditCommand(accountId, amount,
						nov(2012, 10)))
				.expectException(IllegalStateException.class);

	}

	@Test
	public void throwExceptionWhenCreditTransferedGivenAccountIsExpired()
			throws Throwable {
		final Long accountId = 1L;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period
		fixture.given(new CreditAccountCreatedEvent(accountId, 100),
				new CreditAccountMadeEffectiveEvent(start, end))
				.when(new TransferCreditCommand(accountId, -80, nov(2012, 13)))
				.expectException(IllegalStateException.class);

	}

	@Test
	public void throwExceptionWhenCreditTransferedGivenNotEnoughCredits()
			throws Throwable {
		final Long accountId = 1L;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period
		fixture.given(new CreditAccountCreatedEvent(accountId, 100),
				new CreditAccountMadeEffectiveEvent(start, end))
				.when(new TransferCreditCommand(accountId, -1000, nov(2012, 10)))
				.expectException(IllegalStateException.class);

	}

	@Test
	public void expired() throws Throwable {
		final Long accountId = 1L;
		final int amount = 100;
		final Date start = nov(2011, 12);
		final Date end = nov(2012, 12);// a year effective period

		fixture.given(new CreditAccountCreatedEvent(accountId, amount),
				new CreditAccountMadeEffectiveEvent(start, end))
				.when(new ExpireCreditAccountCommand(accountId))
				.expectEvents(new CreditAccountExpiredEvent(accountId));

	}

	private Date nov(int year, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.NOVEMBER, day);
		return c.getTime();
	}

}
