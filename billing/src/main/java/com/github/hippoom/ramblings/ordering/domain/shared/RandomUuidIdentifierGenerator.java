package com.github.hippoom.ramblings.ordering.domain.shared;

import java.util.UUID;

public class RandomUuidIdentifierGenerator implements
		IdentifierGenerator<String> {

	@Override
	public String next() {
		return UUID.randomUUID().toString();
	}

}
