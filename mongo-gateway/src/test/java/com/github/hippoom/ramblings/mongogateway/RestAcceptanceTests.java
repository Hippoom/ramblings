package com.github.hippoom.ramblings.mongogateway;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/admin-servlet.xml", "classpath:root.xml" })
@WebAppConfiguration
public class RestAcceptanceTests {
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private MongoClient mongoClient;

	private MockMvc mockMvc;

	private Object _id;

	@Before
	public void fixture() throws Exception {
		initWac();
		clean();
		this._id = savesAProduct();
	}

	private void clean() {
		mongoClient.getDB("sales").getCollection("products").drop();
	}

	private void initWac() {
		mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void queryAfterSaving() throws Exception {
		mockMvc.perform(
				get("/sales/products/" + this._id).contentType(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("statusCode").value("STATUS_CODE_SUCCESS"))
				.andExpect(jsonPath("payload").value(not(nullValue())));
	}

	@Test
	public void updates() throws Exception {
		mockMvc.perform(
				post("/sales/products/" + this._id).param("payload",
						"{\"alias\":\"hotelOne\", \"price\":\"20.00\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("statusCode").value("STATUS_CODE_SUCCESS"))
				.andExpect(jsonPath("payload").value(not(nullValue())));
	}

	private Object savesAProduct() throws Exception {
		final MvcResult result = mockMvc
				.perform(
						put("/sales/products").param("payload",
								"{\"name\":\"hotel1\"}").contentType(
								MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("statusCode").value("STATUS_CODE_SUCCESS"))
				.andExpect(jsonPath("payload").value(not(nullValue())))
				.andReturn();

		final String response = result.getResponse().getContentAsString();
		final Map map = new ObjectMapper().readValue(response, HashMap.class);
		return map.get("payload");
	}

}
