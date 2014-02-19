package com.github.hippoom.ramblings.dss;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context-infrastructure-jms.xml" })
public class DurableSubscriberTests {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination topic;

	@Resource(name = "subscriber1")
	private SubscriberStub sub1;

	@Resource(name = "subscriber2")
	private SubscriberStub sub2;

	@Test
	public void messageReceived() throws Throwable {

		jmsTemplate.convertAndSend(topic, "hi1");
		
		Thread.sleep(1000);
		
		assertThat(sub1.received(), equalTo("hi1"));
		assertThat(sub2.received(), equalTo("hi1"));
		
		//await().atMost(3, TimeUnit.SECONDS).untilCall(sub1.received(), equalTo("hi1"));
		//await().atMost(3, TimeUnit.SECONDS).untilCall(sub2.received(), equalTo("hi1"));
	}
}
