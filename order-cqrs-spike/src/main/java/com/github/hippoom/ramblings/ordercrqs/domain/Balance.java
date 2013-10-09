package com.github.hippoom.ramblings.ordercrqs.domain;

import static com.github.hippoom.ramblings.ordercrqs.domain.BalanceStatus.BALANCED;
import static com.github.hippoom.ramblings.ordercrqs.domain.BalanceStatus.UNBALANCED;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

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

	protected static Balance reconsititue(final BigDecimal totalAmount,
			final BigDecimal paid) {
		return new Balance(totalAmount, paid);
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
