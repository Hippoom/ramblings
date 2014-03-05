package com.github.hippoom.ramblings.gordering.domain;

import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.github.hippoom.ramblings.booking.events.ReservationSpecificationHandledEvent;
import com.github.hippoom.ramblings.gordering.commands.OrderingService;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderAsWaitPaymentCommand;
import com.github.hippoom.ramblings.gordering.commands.UpdateOrderLineAmountCommand;
import com.github.hippoom.ramblings.gordering.events.OrderPlacedEventFixture;
import com.github.hippoom.ramblings.gordering.events.ReservationSpecificationRequestedEvent;

public class OrderFullfillingSagaUnitTests {
	private AnnotatedSagaTestFixture fixture = new AnnotatedSagaTestFixture(
			OrderFullfillingSaga.class);

	@Before
	public void inject() {
		fixture.registerCommandGateway(OrderingService.class);
	}

	@Test
	public void updatesOrderLineAmountWhenAReservationSpecIsHandled()
			throws Throwable {

		final String trackingId = "t1";
		final String rs1 = "a reservation spec";
		final String rs2 = "another reservation spec";
		final String rId1 = "reservation id for rs1";

		fixture.givenAPublished(new OrderPlacedEventFixture(trackingId).build())
				.andThenAPublished(
						new ReservationSpecificationRequestedEvent(trackingId,
								1, rs1))
				.andThenAPublished(
						new ReservationSpecificationRequestedEvent(trackingId,
								2, rs2))
				.whenPublishingA(
						new ReservationSpecificationHandledEvent(rId1,
								trackingId, 1, 200.0))
				.expectDispatchedCommandsEqualTo(
						new UpdateOrderLineAmountCommand(trackingId, 1, 200.0))
				.expectActiveSagas(1);

	}

	@Test
	public void updatesOrderAsWaitPaymentWhenAllReservationSpecsAreHandled()
			throws Throwable {

		final String trackingId = "t1";
		final String rs1 = "a reservation spec";
		final String rs2 = "another reservation spec";
		final String rId1 = "reservation id for rs1";
		final String rId2 = "reservation id for rs2";

		fixture.givenAPublished(new OrderPlacedEventFixture(trackingId).build())
				.andThenAPublished(
						new ReservationSpecificationRequestedEvent(trackingId,
								1, rs1))
				.andThenAPublished(
						new ReservationSpecificationRequestedEvent(trackingId,
								2, rs2))
				.andThenAPublished(
						new ReservationSpecificationHandledEvent(rId1,
								trackingId, 1, 100.0))
				.whenPublishingA(
						new ReservationSpecificationHandledEvent(rId2,
								trackingId, 2, 200.0))
				.expectDispatchedCommandsEqualTo(
						new UpdateOrderLineAmountCommand(trackingId, 2, 200.0),
						new UpdateOrderAsWaitPaymentCommand(trackingId))
				.expectActiveSagas(0);

	}
}
