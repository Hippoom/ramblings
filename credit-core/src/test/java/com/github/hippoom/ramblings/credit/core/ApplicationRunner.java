package com.github.hippoom.ramblings.credit.core;

import java.util.Date;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"context-infrastructure-cqrs.xml");
		CommandGateway runner = applicationContext
				.getBean(CommandGateway.class);
		final Long accountId = 3L;
		runner.send(new CreateCreditAccountCommand(accountId, 0));

		runner.send(new MakeCreditAccountEffectiveCommand(accountId,
				new Date(), new Date()));
	}

}
