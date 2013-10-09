package com.github.hippoom.ramblings.ordercrqs.domain;

import static com.github.hippoom.ramblings.ordercrqs.domain.Order.BalanceStatus.UNBALANCED;

import java.math.BigDecimal;
import java.util.Date;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.ordercrqs.command.MakePaymentCommand;
import com.github.hippoom.ramblings.ordercrqs.command.ModifyBookingContactCommand;
import com.github.hippoom.ramblings.ordercrqs.command.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordercrqs.event.BalanceRecalculatedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.BookingContactGivenEvent;
import com.github.hippoom.ramblings.ordercrqs.event.BookingContactModifiedEvent;
import com.github.hippoom.ramblings.ordercrqs.event.OrderPlacedEvent;

public class OrderUnitTests {
	private FixtureConfiguration<Order> fixture = Fixtures
			.newGivenWhenThenFixture(Order.class);
	private String trackingId = "1";
	private Date whenPlaced = new Date();
	private String bookingContact = "John Doe";
	private BigDecimal totalAmount = BigDecimal.valueOf(100);

	@Test
	public void publishesOrderPlacedEventWhenHandlesPlaceOrderCommand()
			throws Throwable {
		fixture.given()
				.when(new PlaceOrderCommand(trackingId, whenPlaced,
						bookingContact, totalAmount))
				.expectEvents(
						new OrderPlacedEvent(trackingId, whenPlaced,
								totalAmount),
						new BookingContactGivenEvent(trackingId, bookingContact));

	}

	@Test
	public void publishesBookingContactModifiedEventWhenHandlesModifyBookingContactCommand()
			throws Throwable {
		final String newBookingContact = "Geroge O'Malley";

		fixture.given(new OrderPlacedEvent(trackingId, whenPlaced, totalAmount))
				.when(new ModifyBookingContactCommand(trackingId,
						newBookingContact))
				.expectEvents(
						new BookingContactModifiedEvent(trackingId,
								newBookingContact));

	}

	@Test
	public void publishesBalanceRecalculatedEventWhenHandlesMakePaymentCommand()
			throws Throwable {
		final BigDecimal paid = BigDecimal.valueOf(10);
		final BigDecimal amount = BigDecimal.valueOf(20);

		fixture.given(
				new OrderPlacedEvent(trackingId, whenPlaced, totalAmount),
				new BalanceRecalculatedEvent(trackingId, this.totalAmount,
						paid, UNBALANCED.getValue()))
				.when(new MakePaymentCommand(trackingId, amount))
				.expectEvents(
						new BalanceRecalculatedEvent(trackingId,
								this.totalAmount, paid.add(amount), UNBALANCED
										.getValue()));

	}

}
