package com.github.hippoom.ramblings.mongo;

import java.net.UnknownHostException;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDbFactoryBean extends AbstractFactoryBean<DB> {
	private MongoClient client;

	public MongoDbFactoryBean() throws UnknownHostException {
		this.client = new MongoClient();
	}

	@Override
	protected DB createInstance() throws Exception {
		return client.getDB("billing");
	}

	@Override
	public Class<?> getObjectType() {
		return DB.class;
	}

}
