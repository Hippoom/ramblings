package com.github.hippoom.ramblings.credit.core;

import java.util.Calendar;
import java.util.Date;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class CreditAccountUnitTests {

	private static final Date DATE_EXPIRED = nov(2012, 13);
	private static final Date EFFECTIVE_END = nov(2012, 12);
	private static final Date EFFECTIVE_START = nov(2011, 12);
	private static final long ACCOUNT_ID = 1L;
	private static final int EQUAL_OR_LESS_THAN_BALANCE = 100;
	private static final int GREATER_THAN_BALANCE = 110;
	private static final int INITIAL_BALANCE = 100;
	private FixtureConfiguration<CreditAccount> fixture;

	private static final CreateCreditAccountCommand CREATE_ACCOUNT = new CreateCreditAccountCommand(
			ACCOUNT_ID, INITIAL_BALANCE, EFFECTIVE_START, EFFECTIVE_END);
	private static final CreditAccountBalanceChangedEvent INTIAL_BALANCE_CHANGED = new CreditAccountBalanceChangedEvent(
			CREATE_ACCOUNT.getAccountId(), CREATE_ACCOUNT.getAmount());
	private static final CreditAccountCreatedEvent CREDIT_ACCOUNT_CREATED = new CreditAccountCreatedEvent(
			CREATE_ACCOUNT.getAccountId(),
			CREATE_ACCOUNT.getEffectiveDateRange());

	@Before
	public void setUp() throws Throwable {
		fixture = Fixtures.newGivenWhenThenFixture(CreditAccount.class);

	}

	@Test
	public void creditAccountCreated() throws Throwable {

		fixture.given().when(CREATE_ACCOUNT)
				.expectEvents(CREDIT_ACCOUNT_CREATED, INTIAL_BALANCE_CHANGED);

	}

	@Test
	public void creditAccountBalanceChangedWhenCreditConsumed()
			throws Throwable {
		ConsumeCreditCommand consumeCredit = new ConsumeCreditCommand(
				ACCOUNT_ID, EQUAL_OR_LESS_THAN_BALANCE, EFFECTIVE_END);

		fixture.given(CREDIT_ACCOUNT_CREATED, INTIAL_BALANCE_CHANGED)
				.when(consumeCredit)
				.expectEvents(
						new CreditAccountBalanceChangedEvent(consumeCredit
								.getAccountId(), consumeCredit
								.getAmountConsumed()));

	}

	@Test
	public void throwExceptionWhenCreditTransferedGivenAccountIsExpired()
			throws Throwable {

		fixture.given(CREDIT_ACCOUNT_CREATED, INTIAL_BALANCE_CHANGED)
				.when(consumeExpiredCredits())
				.expectException(IllegalStateException.class);

	}

	private ConsumeCreditCommand consumeExpiredCredits() {
		return new ConsumeCreditCommand(ACCOUNT_ID,
				EQUAL_OR_LESS_THAN_BALANCE, DATE_EXPIRED);
	}

	@Test
	public void throwExceptionWhenCreditTransferedGivenNotEnoughCredits()
			throws Throwable {
		fixture.given(CREDIT_ACCOUNT_CREATED, INTIAL_BALANCE_CHANGED)
				.when(consumeTooManyCredits())
				.expectException(IllegalStateException.class);

	}

	private ConsumeCreditCommand consumeTooManyCredits() {
		return new ConsumeCreditCommand(ACCOUNT_ID,
				GREATER_THAN_BALANCE, EFFECTIVE_END);
	}

	private static Date nov(int year, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.NOVEMBER, day);
		return c.getTime();
	}

}
