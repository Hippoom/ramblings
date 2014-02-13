package com.github.hippoom.ramblings.contracting;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ContractResolverUnitTests {

	@Test
	public void returnsOriginTextIfContainsNonePlaceHolder() throws Throwable {
		final ContractResolver subject = new ContractResolver(
				"Some Text here.", new HashMap<String, String>());

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text here."));
	}

	@Test
	public void returnsValueReplacedTextIfFound() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Replaced");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} here.", placeHolders);

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text Replaced here."));
	}

	@Test
	public void returnsValueReplacedTextIfMultiplePlaceHoldersFound()
			throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Replaced1");
		placeHolders.put("key2", "Replaced2");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} her${key2}e.", placeHolders);

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text Replaced1 herReplaced2e."));
	}

	@Test
	public void returnsKeysIfSomePlaceHoldersMissing() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Replaced1");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} her${key2}e.", placeHolders);

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text Replaced1 her${key2}e."));
	}

	@Test
	public void returnsKeysIfNestedPlaceHolders() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Rep${key2}d1");
		placeHolders.put("key2", "Rep${key3}d2");
		placeHolders.put("key3", "Replaced3");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} here.", placeHolders);

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text RepRepReplaced3d2d1 here."));
	}
}
