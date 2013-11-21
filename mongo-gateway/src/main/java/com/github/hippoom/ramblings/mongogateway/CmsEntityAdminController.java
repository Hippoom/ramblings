package com.github.hippoom.ramblings.mongogateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Controller
public class CmsEntityAdminController {
	@Autowired
	private MongoClient mongoClient;

	@RequestMapping(value = "/{database}/{collection}", method = RequestMethod.PUT)
	@ResponseBody
	public RestResponse<Object> save(@PathVariable("database") String database,
			@PathVariable("collection") String collection,
			@RequestParam("payload") String payload) throws JsonParseException,
			JsonMappingException, IOException {
		Map map = new ObjectMapper().readValue(payload, HashMap.class);
		ObjectId result = new ObjectId();
		BasicDBObject mongoProduct = new BasicDBObject(map);
		mongoProduct.put("_id", result);
		mongoClient.getDB(database).getCollection(collection)
				.insert(mongoProduct);
		return new RestResponse<Object>(result.toStringMongod());

	}

	@RequestMapping(value = "/{database}/{collection}/{_id}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> findOne(
			@PathVariable("database") String database,
			@PathVariable("collection") String collection,
			@PathVariable("_id") String _id) {
		final DBObject doc = mongoClient.getDB(database)
				.getCollection(collection)
				.findOne(new BasicDBObject("_id", new ObjectId(_id)));
		return new RestResponse<String>(doc.toString());

	}

	@RequestMapping(value = "/{database}/{collection}/{_id}", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<String> update(
			@PathVariable("database") String database,
			@PathVariable("collection") String collection,
			@PathVariable("_id") String _id,
			@RequestParam("payload") String payload) throws JsonParseException,
			JsonMappingException, IOException {

		Map map = new ObjectMapper().readValue(payload, HashMap.class);
		ObjectId result = new ObjectId();
		BasicDBObject mongoProduct = new BasicDBObject(map);

		mongoClient
				.getDB(database)
				.getCollection(collection)
				.update(new BasicDBObject("_id", new ObjectId(_id)),
						mongoProduct);
		return new RestResponse<String>(_id);

	}
}
