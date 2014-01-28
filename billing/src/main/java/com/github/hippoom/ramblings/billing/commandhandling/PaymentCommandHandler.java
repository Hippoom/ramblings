package com.github.hippoom.ramblings.billing.commandhandling;

import lombok.Setter;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;

import com.github.hippoom.ramblings.billing.commands.GatherPaymentCommand;
import com.github.hippoom.ramblings.billing.commands.MakePaymentCommand;
import com.github.hippoom.ramblings.billing.domain.model.payment.Payment;
import com.github.hippoom.ramblings.billing.domain.shared.IdentifierGenerator;

public class PaymentCommandHandler {
	@Setter
	private IdentifierGenerator<String> paymentSequenceGenerator;
	@Setter
	private Repository<Payment> paymentRepository;

	@CommandHandler
	public String handle(MakePaymentCommand command) {
		final Payment payment = new Payment(paymentSequenceGenerator.next(),
				command.getOrderId(), command.getAmount(), command.getBy(),
				command.getReceiptNo());
		paymentRepository.add(payment);
		return payment.sequence();
	}

	@CommandHandler
	public void handle(GatherPaymentCommand command) {
		final Payment payment = paymentRepository.load(command.getSequence());
		payment.gathered();
	}

}
