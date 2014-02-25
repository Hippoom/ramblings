package com.github.hippoom.ramblings.ordering.domain.shared;

public interface IdentifierGenerator<T> {
	T next();
}
