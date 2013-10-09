package com.github.hippoom.ramblings.ordercrqs.domain;

import static com.github.hippoom.ramblings.ordercrqs.domain.Order.BalanceStatus.*;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import com.github.hippoom.ramblings.ordercrqs.domain.Order.BalanceStatus;
import com.github.hippoom.ramblings.ordercrqs.event.BalanceRecalculatedEvent;

import lombok.Getter;

/**
 * <pre>
 * Value object for calculating order balance.
 * 
 * Value object is side effect free since it's immutable.
 * 
 * Value object is appropriate for describing behavior.
 * 
 * </pre>
 * 
 */
public class Balance {
	@Getter
	private final BigDecimal totalAmount;
	@Getter
	private final BigDecimal paid;

	private Balance(BigDecimal totalAmount, BigDecimal paid) {
		this.totalAmount = totalAmount;
		this.paid = paid;
	}

	public static Balance whenPlaced(BigDecimal totalAmount) {
		return new Balance(totalAmount, ZERO);
	}

	public static Balance from(BalanceRecalculatedEvent event) {
		return new Balance(event.getTotalAmount(), event.getPaid());
	}

	public Balance paidWith(BigDecimal amount) {
		return new Balance(totalAmount, this.paid.add(amount));
	}

	public BalanceStatus getStatus() {
		if (totalAmount.compareTo(paid) != 0) {
			return UNBALANCED;
		} else {
			return BALANCED;
		}
	}

}
