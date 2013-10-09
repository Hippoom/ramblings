package com.github.hippoom.ramblings.ordercqrs;

import static com.github.hippoom.ramblings.ordercqrs.domain.BalanceStatus.BALANCED;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hippoom.ramblings.ordercqrs.command.DiscountingCommand;
import com.github.hippoom.ramblings.ordercqrs.command.MakePaymentCommand;
import com.github.hippoom.ramblings.ordercqrs.command.ModifyBookingContactCommand;
import com.github.hippoom.ramblings.ordercqrs.command.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordercqrs.query.detail.OrderDetailReadModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root.xml" })
public class AcceptanceTests {
	@Autowired
	private CommandGateway commandGateway;

	@PersistenceUnit
	private EntityManagerFactory em;

	@Test
	public void placesOrder_thenModifiesBookingContext_thenDiscounts_thenMakesPayment()
			throws Throwable {
		final String trackingId = nextIdentifier();
		final Date today = today("20:12:44");

		send(new PlaceOrderCommand(trackingId, today, "John Doe", with(100)));

		send(new ModifyBookingContactCommand(trackingId, "Geroge O'Malley"));

		send(new DiscountingCommand(trackingId, with(30)));

		send(new MakePaymentCommand(trackingId, with(70)));

		waitForSynchronization();

		final OrderDetailReadModel order = queryDetail(trackingId);

		assertThat(order.getBookingContactName(), equalTo("Geroge O'Malley"));
		assertThat(order.getWhenPlaced(), comparesEqualTo(today));
		assertThat(order.getTotalAmount(), equalTo(with(70).doubleValue()));
		assertThat(order.getTotalPaid(), equalTo(with(70).doubleValue()));
		assertThat(order.getBalanceStatus(), equalTo(BALANCED.getValue()));
	}

	private void waitForSynchronization() throws InterruptedException {
		Thread.sleep(1000);
	}

	private OrderDetailReadModel queryDetail(String trackingId) {
		return em.createEntityManager().find(OrderDetailReadModel.class,
				trackingId);
	}

	private void send(Object command) {
		commandGateway.sendAndWait(command);
	}

	private BigDecimal with(int amount) {
		return BigDecimal.valueOf(amount);
	}

	private Date today(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());

		today += (" " + time);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(today);
	}

	private String nextIdentifier() {
		return UUID.randomUUID().toString();
	}
}
