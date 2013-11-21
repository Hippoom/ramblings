package com.github.hippoom.ramblings.jobs;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:messaging.xml")
public class JmsRequestReplyLearningTests {

	@Resource(name = "outChannel")
	private MessageChannel in;

	@Resource(name = "replyChannel")
	private PollableChannel out;

	@Test
	public void sendsMessageThroughGateway() throws Throwable {
		in.send(MessageBuilder.withPayload("Hello World").build());

		Message<?> received = out.receive();

		assertThat((String) received.getPayload(), equalTo("Hello World"));
	}
}
