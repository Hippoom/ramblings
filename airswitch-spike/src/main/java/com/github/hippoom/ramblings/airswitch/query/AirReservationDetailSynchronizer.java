package com.github.hippoom.ramblings.airswitch.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.transaction.annotation.Transactional;

import com.github.hippoom.ramblings.airswitch.command.reservation.AirReservationCreatedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.AirTicketSubtotalSummedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.BookingContactUpdatedEvent;
import com.github.hippoom.ramblings.airswitch.command.reservation.ItineraryUpdatedEvent;

@Transactional
public class AirReservationDetailSynchronizer {
	@PersistenceContext
	private EntityManager entityManager;

	@EventHandler
	public void on(AirReservationCreatedEvent event) {

		final AirReservationDetail reservation = new AirReservationDetail();
		reservation.id = event.getReservationId();
		reservation.status = event.getStatus().name();

		entityManager.persist(reservation);
	}

	@EventHandler
	public void on(BookingContactUpdatedEvent event) {

		final AirReservationDetail reservation = entityManager.find(
				AirReservationDetail.class, event.getReservationId());

		reservation.booking_contact = event.getBookingContact();

		entityManager.persist(reservation);
	}

	@EventHandler
	public void on(ItineraryUpdatedEvent event) {

		final AirReservationDetail reservation = entityManager.find(
				AirReservationDetail.class, event.getReservationId());

		reservation.itinerary = event.getItinerary();

		entityManager.persist(reservation);
	}

	@EventHandler
	public void on(AirTicketSubtotalSummedEvent event) {

		final AirReservationDetail reservation = entityManager.find(
				AirReservationDetail.class, event.getReservationId());

		reservation.total_amount = event.getCurrentTotal();

		entityManager.persist(reservation);
	}
}
