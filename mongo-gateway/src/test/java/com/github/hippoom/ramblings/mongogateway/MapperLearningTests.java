package com.github.hippoom.ramblings.mongogateway;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;

public class MapperLearningTests {

	@Test
	public void savesAProduct() throws Throwable {
		final String src = "{\"name\":\"refactoring\", \"count\":1, \"tags\":[\"book\", \"software\"]}";
		Map product = new ObjectMapper().readValue(src, HashMap.class);

		BasicDBObject mongoProduct = new BasicDBObject(product);
		System.err.println(mongoProduct);

		assertThat(mongoProduct.getString("name"), equalTo("refactoring"));
		assertThat(mongoProduct.getString("count"), equalTo("1"));
		assertThat(mongoProduct.get("tags"),
				equalTo((Object) Arrays.asList("book", "software")));
	}
}
