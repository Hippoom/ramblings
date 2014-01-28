package com.github.hippoom.ramblings.billing.persistence.mongo;

import lombok.Setter;

import com.github.hippoom.ramblings.billing.query.ReceiptView;
import com.github.hippoom.ramblings.billing.query.ReceiptViewStore;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoReceiptViewStore implements ReceiptViewStore {
	@Setter
	private DB billingDb;

	@Override
	public void addAmount(String receiptNo, double amount) {
		getCollection().findAndModify(
				new BasicDBObject("receiptNo", receiptNo), null, null, false,
				new BasicDBObject("$inc", new BasicDBObject("amount", amount)),
				false, true);
	}

	private DBCollection getCollection() {
		return billingDb.getCollection("receipts");
	}

	@Override
	public ReceiptView findBy(String receiptNo) {
		DBObject receiptDoc = getCollection().findOne(
				new BasicDBObject("receiptNo", receiptNo));
		return receiptDoc == null ? null : new ReceiptView(
				(String) receiptDoc.get("receiptNo"),
				(Double) receiptDoc.get("amount"));
	}

}
