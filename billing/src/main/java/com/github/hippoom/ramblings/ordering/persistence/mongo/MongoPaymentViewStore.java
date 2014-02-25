package com.github.hippoom.ramblings.ordering.persistence.mongo;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.github.hippoom.ramblings.ordering.domain.model.gathering.Status;
import com.github.hippoom.ramblings.ordering.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentMadeEvent;
import com.github.hippoom.ramblings.ordering.query.PaymentView;
import com.github.hippoom.ramblings.ordering.query.PaymentViewStore;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Slf4j
public class MongoPaymentViewStore implements PaymentViewStore {
	@Setter
	private DB billingDb;

	@EventHandler
	public void on(PaymentMadeEvent event) {
		log.info("Receiving " + event.toString());
		getCollection().save(
				new BasicDBObject("sequence", event.getSequence())
						.append("orderId", event.getOrderId())
						.append("amount", event.getAmount())
						.append("by", event.getBy())
						.append("status", Status.NEW.name()));
	}

	@EventHandler
	public void on(PaymentGatheredEvent event) {
		log.info("Receiving " + event.toString());

		getCollection().update(
				new BasicDBObject("sequence", event.getSequence()),
				new BasicDBObject("$set", new BasicDBObject("status",
						Status.GATHERED.name())));
	}

	private DBCollection getCollection() {
		return billingDb.getCollection("payments");
	}

	@Override
	public PaymentView sequenceAs(String sequence) {
		DBObject doc = getCollection().findOne(
				new BasicDBObject("sequence", sequence));
		if (doc == null) {
			return null;
		}
		return new PaymentView((String) doc.get("sequence"),
				(String) doc.get("orderId"), (Double) doc.get("amount"),
				(String) doc.get("by"));
	}
}
