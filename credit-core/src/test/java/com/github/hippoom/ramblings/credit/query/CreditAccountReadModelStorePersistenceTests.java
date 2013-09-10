package com.github.hippoom.ramblings.credit.query;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:query-persistence.xml")
public class CreditAccountReadModelStorePersistenceTests {

	@Autowired
	private CreditAccountReadModelStore store;

	@Test
	public void saves() throws Throwable {
		CreditAccountReadModel account = new CreditAccountReadModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		final long id = 1L;
		final Date start = sdf.parse("2012-01-01");
		final Date end = sdf.parse("2012-01-02");
		final int balance = 100;

		account.setId(id);
		account.setEffectiveStart(start);
		account.setEffectiveEnd(end);
		account.setBalance(balance);
		
		store.store(account);

		CreditAccountReadModel saved = store.findBy(id);

		assertThat(saved.getEffectiveStart(), equalTo(start));
		assertThat(saved.getEffectiveEnd(), equalTo(end));
		assertThat(saved.getBalance(), equalTo(balance));
	}

}
