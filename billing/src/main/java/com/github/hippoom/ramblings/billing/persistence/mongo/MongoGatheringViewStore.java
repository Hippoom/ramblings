package com.github.hippoom.ramblings.billing.persistence.mongo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.github.hippoom.ramblings.billing.query.GatheringView;
import com.github.hippoom.ramblings.billing.query.GatheringViewStore;
import com.github.hippoom.ramblings.ordering.events.OrderPlacedEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentGatheredEvent;
import com.github.hippoom.ramblings.ordering.events.PaymentMadeEvent;
import com.github.hippoom.ramblings.ordering.query.PaymentView;
import com.github.hippoom.ramblings.ordering.query.PaymentViewStore;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Slf4j
public class MongoGatheringViewStore implements GatheringViewStore {
	@Setter
	private DB billingDb;
	@Setter
	private PaymentViewStore payments;

	@EventHandler
	public void on(OrderPlacedEvent event) {
		log.info("Receiving " + event.toString());
		getCollection().save(
				new BasicDBObject("trackingId", event.getTrackingId()).append(
						"total", event.getTotal()));
	}

	@EventHandler
	public void on(PaymentMadeEvent event) {
		log.info("Receiving " + event.toString());
		getCollection().findAndModify(
				new BasicDBObject("trackingId", event.getOrderId()),
				new BasicDBObject("$inc", new BasicDBObject("paid."
						+ event.getBy(), event.getAmount())).append(
						"$addToSet",
						new BasicDBObject("receipts", event.getReceiptNo())));
	}

	@EventHandler
	public void on(PaymentGatheredEvent event) {
		log.info("Receiving " + event.toString());

		PaymentView payment = payments.sequenceAs(event.getSequence());


		
		
		
		getCollection().findAndModify(
				new BasicDBObject("trackingId", payment.getOrderId()),
				new BasicDBObject("$inc", new BasicDBObject("paid."
						+ payment.getBy(), new BigDecimal(payment.getAmount())
						.negate().doubleValue()).append("gathered." + payment.getBy(),
										payment.getAmount())));
	}

	private DBCollection getCollection() {
		return billingDb.getCollection("gatherings");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public GatheringView trackingIdAs(String trackingId) {
		DBObject doc = getCollection().findOne(
				new BasicDBObject("trackingId", trackingId));
		if (doc == null) {
			return null;
		}
		final String orderId = (String) doc.get("trackingId");
		final double total = (Double) doc.get("total");
		final List<String> receipts = (List) doc.get("receipts");
		DBObject paidDoc = (DBObject) doc.get("paid");
		final Map<String, Double> paid = new HashMap<String, Double>();
		for (String by : paidDoc.keySet()) {
			paid.put(by, (Double) paidDoc.get(by));
		}
		DBObject gatheredDoc = (DBObject) doc.get("gathered");
		final Map<String, Double> gathered = new HashMap<String, Double>();
		if (gatheredDoc != null) {

			for (String by : gatheredDoc.keySet()) {
				gathered.put(by, (Double) gatheredDoc.get(by));
			}
		}
		return doc == null ? null : new GatheringView(orderId, total, receipts,
				paid, gathered);
	}
}
