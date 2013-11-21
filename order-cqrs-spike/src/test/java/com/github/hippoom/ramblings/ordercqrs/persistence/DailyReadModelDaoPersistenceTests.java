package com.github.hippoom.ramblings.ordercqrs.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.hippoom.ramblings.ordercqrs.query.daily.DailyOrderCountEntryReadModel;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class DailyReadModelDaoPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@DatabaseSetup(value = "classpath:daily_read_model_save_fixture.xml")
	@ExpectedDatabase(value = "classpath:daily_read_model_save_expect.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	public void savesDailyOrderCountEntry() throws Throwable {

		final DailyOrderCountEntryReadModel expect = new DailyOrderCountEntryReadModel();

		expect.setTrackingId("3");
		expect.setToday("2011-01-01");

		save(expect);
	}

	private void save(final DailyOrderCountEntryReadModel target) {
		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallback<DailyOrderCountEntryReadModel>() {

					@Override
					public DailyOrderCountEntryReadModel doInTransaction(
							TransactionStatus status) {
						entityManager.persist(target);
						return null;
					}

				});
	}

}
