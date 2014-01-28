package com.github.hippoom.ramblings.billing.domain.shared;

public interface IdentifierGenerator<T> {
	T next();
}
