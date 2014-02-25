package com.github.hippoom.ramblings.ordering.commandhandling;

import lombok.Setter;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;

import com.github.hippoom.ramblings.ordering.commands.GatherPaymentCommand;
import com.github.hippoom.ramblings.ordering.commands.MakePaymentCommand;
import com.github.hippoom.ramblings.ordering.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.ordering.domain.model.gathering.By;
import com.github.hippoom.ramblings.ordering.domain.model.order.Order;
import com.github.hippoom.ramblings.ordering.domain.shared.IdentifierGenerator;

public class OrderCommandHandler {
	@Setter
	private IdentifierGenerator<String> trackingIdGenerator;
	@Setter
	private IdentifierGenerator<String> sequenceGenerator;
	@Setter
	private Repository<Order> orderRepository;

	@CommandHandler
	public String handle(PlaceOrderCommand command) {
		final Order order = new Order(trackingIdGenerator.next(),
				command.getTotal());
		orderRepository.add(order);
		return order.trackingId();
	}

	@CommandHandler
	public String handle(MakePaymentCommand command) {
		final Order order = orderRepository.load(command.getOrderId());
		String next = sequenceGenerator.next();
		order.makePaymentWith(next, command.getAmount(),
				By.valueOf(command.getBy()), command.getReceiptNo());
		return next;
	}

	@CommandHandler
	public void handle(GatherPaymentCommand command) {

		final Order order = orderRepository.load(command.getOrderId());
		order.gatherPaymentWith(command.getSequence());

	}
}
