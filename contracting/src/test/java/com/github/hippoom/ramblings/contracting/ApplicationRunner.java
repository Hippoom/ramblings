package com.github.hippoom.ramblings.contracting;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ApplicationRunner {
	private ContractingService subject = new ContractingService();

	@Before
	public void inject() {
		subject.add("agreement item 1",
				"Some plain text with ${agreement item 1-1} and ${agreement item 1-2}");
		subject.add("agreement item 1-1",
				"Some plain text with ${productSpecificKey1}");
		subject.add("agreement item 1-2", "Plain text for agreement item 1-2");
		subject.add("agreement item 2",
				"Some plain text with ${productSpecificKey2}");
		subject.add("agreement item 3", "Plain text for agreement item 3");
		subject.add("agreement item 4", "This one is not used");
	}

	@Test
	public void good() throws Throwable {

		final String origin = "This is contract v1. Contains ${agreement item 1}, ${agreement item 2} and ${agreement item 3}.";
		final Map<String, String> productSpecificPlaceHolders = new HashMap<String, String>();
		productSpecificPlaceHolders.put("productSpecificKey1",
				"productSpecificValue1");
		productSpecificPlaceHolders.put("productSpecificKey2",
				"productSpecificValue2");

		final ContractResolver resolver = subject.resolve(origin,
				productSpecificPlaceHolders);

		assertThat(
				resolver.resolved(),
				equalTo("This is contract v1. Contains Some plain text with Some plain text with productSpecificValue1 and Plain text for agreement item 1-2, Some plain text with productSpecificValue2 and Plain text for agreement item 3."));

		System.err.println(resolver.placeHoldersInvolved());

		assertThat(resolver.placeHoldersInvolved(),
				not(hasEntry("agreement item 4", "This one is not used")));
	}
}
