package com.github.hippoom.ramblings.billing.commandhandling;

import lombok.Setter;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;

import com.github.hippoom.ramblings.billing.commands.AddOrderPaidCommand;
import com.github.hippoom.ramblings.billing.commands.PlaceOrderCommand;
import com.github.hippoom.ramblings.billing.domain.model.order.Order;
import com.github.hippoom.ramblings.billing.domain.shared.IdentifierGenerator;

public class OrderCommandHandler {
	@Setter
	private IdentifierGenerator<String> trackingIdGenerator;
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
	public void handle(AddOrderPaidCommand command) {
		final Order order = orderRepository.load(command.getOrderId());
		order.addPaid(command.getAugend());
	}
}
