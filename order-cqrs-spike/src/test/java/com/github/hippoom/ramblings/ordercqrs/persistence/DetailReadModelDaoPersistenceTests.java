package com.github.hippoom.ramblings.ordercqrs.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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

import com.github.hippoom.ramblings.ordercqrs.query.detail.OrderDetailReadModel;
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
public class DetailReadModelDaoPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@DatabaseSetup(value = "classpath:detail_read_model_save_fixture.xml")
	@ExpectedDatabase(value = "classpath:detail_read_model_save_expect.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	public void savesOrderDetail() throws Throwable {

		final String prototypeId = "2";
		final String targetId = "3";
		final OrderDetailReadModel prototype = retrieve(prototypeId);

		final OrderDetailReadModel expect = copy(prototype, targetId);

		save(expect);

	}

	@DatabaseSetup(value = "classpath:detail_read_model_update_fixture.xml")
	@ExpectedDatabase(value = "classpath:detail_read_model_update_expect.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Test
	public void updatesOrderDetail() throws Throwable {
		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallback<OrderDetailReadModel>() {

					@Override
					public OrderDetailReadModel doInTransaction(
							TransactionStatus status) {
						final String prototypeId = "4";
						final String targetId = "5";
						final OrderDetailReadModel prototype = retrieve(prototypeId);

						final OrderDetailReadModel target = retrieve(targetId);

						copy(prototype, target);

						entityManager.persist(target);
						return null;
					}

				});
	}

	private void save(final OrderDetailReadModel target) {
		new TransactionTemplate(transactionManager)
				.execute(new TransactionCallback<OrderDetailReadModel>() {

					@Override
					public OrderDetailReadModel doInTransaction(
							TransactionStatus status) {
						entityManager.persist(target);
						return null;
					}

				});
	}

	private void copy(OrderDetailReadModel prototype,
			OrderDetailReadModel target) {

		target.setBookingContactName(prototype.getBookingContactName());
		target.setTotalAmount(prototype.getTotalAmount());
		target.setTotalPaid(prototype.getTotalPaid());
		target.setBalanceStatus(prototype.getBalanceStatus());
	}

	private OrderDetailReadModel copy(OrderDetailReadModel prototype,
			String targetId) {
		ModelMapper mapper = new ModelMapper();
		final OrderDetailReadModel copy = mapper.map(prototype,
				OrderDetailReadModel.class);
		copy.setTrackingId(targetId);
		return copy;
	}

	private OrderDetailReadModel retrieve(String prototypeId) {
		return entityManager.find(OrderDetailReadModel.class, prototypeId);
	}

}
