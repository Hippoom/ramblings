package com.github.hippoom.ramblings.mongogateway;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoJavaDriverLearningTests {

	private MongoClient mongo;
	private DB db;

	@Before
	public void connection() throws Throwable {
		mongo = new MongoClient();
		db = mongo.getDB("sales");
	}

	@Test
	public void connectsToMongo() throws Throwable {
		assertThat(db.getName(), equalTo("sales"));
	}

	@Test
	public void savesAProduct() throws Throwable {
		final DBCollection products = db.getCollection("products");
		BasicDBObject mongoDb = new BasicDBObject("name", "MongoDB").append("type",
				"database").append("count", 1);

		final WriteResult result = products.insert(mongoDb);
		
		System.err.println(result);
		
		final DBObject saved = products.findOne(new BasicDBObject("name", "MongoDB"));
		
		System.err.println(saved);
	}
}
