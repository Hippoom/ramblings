package com.github.hippoom.ramblings.credit.core;

import static com.github.hippoom.ramblings.credit.core.CreditEntry.Status.*;

import java.util.Date;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class CreditEntry extends AbstractAnnotatedAggregateRoot<Long> {

	private static final long serialVersionUID = 1L;

	@AggregateIdentifier
	private Long id;
	private int amount;
	private Date effectiveDateRangeStart;
	private Date effectiveDateRangeEnd;
	private Status status;

	@CommandHandler
	public CreditEntry(CreateCreditEntryCommand command) {
		apply(new CreditEntryCreatedEvent(command.getEntryId(),
				command.getAmount()));
	}

	@EventHandler
	public void on(CreditEntryCreatedEvent event) {
		this.id = event.getEntryId();
		this.amount = event.getAmount();
		this.status = Status.NEW;
	}

	@CommandHandler
	public void markCompleted(MakeCreditEntryEffectiveCommand command) {
		apply(new CreditEntryMadeEffectiveEvent(command.getEntryId(),
				command.getStart(), command.getEnd()));
	}

	@EventHandler
	public void on(CreditEntryMadeEffectiveEvent event) {
		this.effectiveDateRangeStart = event.getStart();
		this.effectiveDateRangeEnd = event.getEnd();
		this.status = Status.EFFECTIVE;
	}

	public CreditEntry() {

	}

	public enum Status {
		NEW, EFFECTIVE, EXPIRED
	}

	public boolean isNew() {
		return is(NEW);
	}

	private boolean is(Status status) {
		return this.status == status;
	}
}
