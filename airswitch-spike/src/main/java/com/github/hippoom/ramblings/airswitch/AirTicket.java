package com.github.hippoom.ramblings.airswitch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings("serial")
public class AirTicket extends AbstractAnnotatedAggregateRoot<Long> {
	@AggregateIdentifier
	private Long id;
	private Long reservationId;
	private Status status;
	private List<AirTicketItem> items = new ArrayList<AirTicketItem>();
	private String number;

	@CommandHandler
	public AirTicket(CreateAirTicketCommand command) {
		double total = 0.00;
		final List<AirTicketItemCreatedEvent> itemCreateds = new ArrayList<AirTicketItemCreatedEvent>();
		Map<Integer, Double> fares = command.getFares();
		Iterator<Entry<Integer, Double>> iterator = fares.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Double> next = iterator.next();
			total += next.getValue();
			itemCreateds.add(new AirTicketItemCreatedEvent(command
					.getTicketId(), next.getKey(), next.getValue()));
		}
		apply(new AirTicketCreatedEvent(command.getTicketId(),
				command.getReservationId(), Status.NEW, total));
		for (AirTicketItemCreatedEvent itemCreated : itemCreateds) {
			apply(itemCreated);
		}
	}

	@CommandHandler
	public void demand(DemandAirTicketCommand command) {
		apply(new AirTicketDemandedEvent(command.getTicketId(),
				command.getNumber(), Status.DEMANDED));
	}

	@CommandHandler
	public void demand(CancelAirTicketCommand command) {
		apply(new AirTicketCanceledEvent(command.getTicketId(),
				command.getPenalty(), Status.CANCELED));
	}

	@EventHandler
	public void on(AirTicketCreatedEvent event) {
		this.id = event.getTicketId();
		this.reservationId = event.getReservationId();
		this.status = event.getStatus();
	}

	@EventHandler
	public void on(AirTicketItemCreatedEvent event) {
		this.items.add(new AirTicketItem(event.getRph(), event.getFare()));
	}

	@EventHandler
	public void on(AirTicketDemandedEvent event) {
		this.status = event.getStatus();
		this.number = event.getNumber();
	}

	@EventHandler
	public void on(AirTicketCanceledEvent event) {
		this.status = event.getStatus();
	}

	public enum Status {
		NEW, DEMANDED, CANCELED
	}

	public AirTicket() {

	}
}
