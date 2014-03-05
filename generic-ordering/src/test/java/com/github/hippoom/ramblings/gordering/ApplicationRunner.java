package com.github.hippoom.ramblings.gordering;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hippoom.ramblings.booking.events.ReservationSpecificationHandledEvent;
import com.github.hippoom.ramblings.gordering.commands.OrderingService;
import com.github.hippoom.ramblings.gordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.gordering.domain.Order;
import com.github.hippoom.ramblings.gordering.query.OrderView;
import com.github.hippoom.ramblings.gordering.test.InMemoryBookingAdapter;
import com.github.hippoom.ramblings.gordering.test.InMemoryOrderQueryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root.xml" })
public class ApplicationRunner implements ApplicationContextAware {
	@Autowired
	private OrderingService orderingService;
	@Autowired
	private InMemoryOrderQueryService orderQueryService;
	@Autowired
	private InMemoryBookingAdapter bookingAdapter;
	@Autowired
	private EventBus eventBus;

	private ApplicationContext applicationContext;

	@Before
	public void clear() throws Throwable {
		FileUtils.deleteDirectory(applicationContext.getResource(
				"file:target/data/gordering").getFile());
	}

	@Test
	public void placesOrderForFlightAndHotelReservations() throws Throwable {
		final String trackingId = "trackingId1";

		orderingService.send(new PlaceOrderCommand(trackingId,
				"some vip member", "MU8754_2014-04-01_1_$100",
				"Garden-Hotel_2014-04-01_1_$100"));

		final OrderView order = orderQueryService.trackingIdAs(trackingId);
		final List<String> reservationSpecs = bookingAdapter.get(order
				.getTrackingId());

		assertThat(order, is(not(nullValue())));
		assertThat(
				reservationSpecs,
				contains("MU8754_2014-04-01_1_$100",
						"Garden-Hotel_2014-04-01_1_$100"));

		eventBus.publish(new GenericEventMessage<ReservationSpecificationHandledEvent>(
				new ReservationSpecificationHandledEvent("flight res id",
						trackingId, 1, 200.0)));
		eventBus.publish(new GenericEventMessage<ReservationSpecificationHandledEvent>(
				new ReservationSpecificationHandledEvent("hotel res id",
						trackingId, 2, 400.0)));

		final OrderView fullfilled = orderQueryService.trackingIdAs(trackingId);

		assertThat(fullfilled.getStatus(),
				equalTo(Order.Status.WAIT_PAYMENT.name()));
		assertThat(fullfilled.getTotalAmount(), equalTo(600.0));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
