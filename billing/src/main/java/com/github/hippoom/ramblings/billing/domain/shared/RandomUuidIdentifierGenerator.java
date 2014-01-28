package com.github.hippoom.ramblings.billing.domain.shared;

import java.util.UUID;

public class RandomUuidIdentifierGenerator implements
		IdentifierGenerator<String> {

	@Override
	public String next() {
		return UUID.randomUUID().toString();
	}

}
