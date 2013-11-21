package com.github.hippoom.ramblings.ordercqrs.domain;

import static com.github.hippoom.ramblings.ordercqrs.domain.BalanceStatus.*;

import java.math.BigDecimal;
import java.util.Date;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.ordercqrs.command.DiscountingCommand;
import com.github.hippoom.ramblings.ordercqrs.command.MakePaymentCommand;
import com.github.hippoom.ramblings.ordercqrs.command.ModifyBookingContactCommand;
import com.github.hippoom.ramblings.ordercqrs.command.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordercqrs.event.OrderDiscountedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.PaymentMadeEvent;
import com.github.hippoom.ramblings.ordercqrs.event.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.ordercqrs.event.OrderPlacedEvent;

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
								totalAmount, BigDecimal.ZERO,
								UNBALANCED.getValue()),
						new BookingContactUpdatedEvent(trackingId,
								bookingContact));

	}

	@Test
	public void publishesBookingContactModifiedEventWhenHandlesModifyBookingContactCommand()
			throws Throwable {
		final String newBookingContact = "Geroge O'Malley";

		fixture.given(
				new OrderPlacedEvent(trackingId, whenPlaced, totalAmount,
						BigDecimal.ZERO, UNBALANCED.getValue()))
				.when(new ModifyBookingContactCommand(trackingId,
						newBookingContact))
				.expectEvents(
						new BookingContactUpdatedEvent(trackingId,
								newBookingContact));

	}

	@Test
	public void publishesBalanceRecalculatedEventWhenHandlesMakePaymentCommand()
			throws Throwable {
		final BigDecimal paid = BigDecimal.valueOf(10);
		final BigDecimal amount = BigDecimal.valueOf(20);

		fixture.given(
				new OrderPlacedEvent(trackingId, whenPlaced, totalAmount,
						BigDecimal.ZERO, UNBALANCED.getValue()),
				new PaymentMadeEvent(trackingId, this.totalAmount, paid,
						UNBALANCED.getValue()))
				.when(new MakePaymentCommand(trackingId, amount))
				.expectEvents(
						new PaymentMadeEvent(trackingId, this.totalAmount, paid
								.add(amount), UNBALANCED.getValue()));

	}

	@Test
	public void publishesBalanceRecalculatedEventWhenHandlesDiscoutingCommand()
			throws Throwable {
		final BigDecimal paid = totalAmount;
		final BigDecimal amount = BigDecimal.valueOf(20);

		fixture.given(
				new OrderPlacedEvent(trackingId, whenPlaced, totalAmount,
						BigDecimal.ZERO, UNBALANCED.getValue()),
				new PaymentMadeEvent(trackingId, this.totalAmount, paid,
						BALANCED.getValue()))
				.when(new DiscountingCommand(trackingId, amount))
				.expectEvents(
						new OrderDiscountedEvent(trackingId, this.totalAmount
								.subtract(amount), paid, UNBALANCED.getValue()));

	}

}
