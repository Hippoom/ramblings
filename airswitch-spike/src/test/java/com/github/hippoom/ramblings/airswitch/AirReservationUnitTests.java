package com.github.hippoom.ramblings.airswitch;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Test;

import com.github.hippoom.ramblings.airswitch.command.reservation.ItineraryUpdatedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservation;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservationCreatedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.CreateAirReservationCommand;

public class AirReservationUnitTests {
	private FixtureConfiguration<AirReservation> fixture = Fixtures
			.newGivenWhenThenFixture(AirReservation.class);

	@Test
	public void airReservationCreated() throws Throwable {
		final Long id = 1L;
		final String bookingContact = "John Doe";
		final String itinerary = "A very wonderful itinerary";

		fixture.given()
				.when(new CreateAirReservationCommand(id, bookingContact,
						itinerary))
				.expectEvents(
						new AirReservationCreatedEvent(id,
								AirReservation.Status.NEW),
						new BookingContactUpdatedEvent(id, bookingContact),
						new ItineraryUpdatedEvent(id, itinerary));

	}
}
