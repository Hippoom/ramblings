package com.github.hippoom.ramblings.contracting;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ContractResolverUnitTests {

	@Test
	public void returnsOriginTextIfContainsNonePlaceHolder() throws Throwable {
		final ContractResolver subject = new ContractResolver(
				"Some Text here.", new HashMap<String, String>());

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text here."));
		assertThat(subject.placeHoldersInvolved().size(), equalTo(0));
	}

	@Test
	public void returnsValueReplacedTextIfFound() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Replaced");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} here.", placeHolders);

		final String resolved = subject.resolved();

		assertThat(resolved, equalTo("Some Text Replaced here."));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key1", "Replaced"));
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
		assertThat(subject.placeHoldersInvolved(),
				hasEntry("key1", "Replaced1"));
		assertThat(subject.placeHoldersInvolved(),
				hasEntry("key2", "Replaced2"));
	}

	@Test
	public void returnsKeysIfOnePlaceHoldersMissing() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Replaced1");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} her${key2}e.", placeHolders);

		final String resolved = subject.resolved();
		final List<String> unresolved = subject.unresolved();

		assertThat(resolved, equalTo("Some Text Replaced1 her${key2}e."));
		assertThat(unresolved, contains("key2"));
		assertThat(subject.placeHoldersInvolved(),
				hasEntry("key1", "Replaced1"));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key2", null));
	}

	@Test
	public void returnsKeysIfMultiplePlaceHoldersMissing() throws Throwable {
		final Map<String, String> placeHolders = new HashMap<String, String>();
		placeHolders.put("key1", "Rep${key2}d${key3}1");
		final ContractResolver subject = new ContractResolver(
				"Some Text ${key1} her${key2}e.", placeHolders);

		final String resolved = subject.resolved();
		final List<String> unresolved = subject.unresolved();

		assertThat(resolved,
				equalTo("Some Text Rep${key2}d${key3}1 her${key2}e."));
		assertThat(unresolved, contains("key2", "key3"));
		assertThat(subject.placeHoldersInvolved(),
				hasEntry("key1", "Rep${key2}d${key3}1"));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key2", null));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key3", null));
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
		assertThat(subject.placeHoldersInvolved(),
				hasEntry("key1", "RepRepReplaced3d2d1"));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key2", "RepReplaced3d2"));
		assertThat(subject.placeHoldersInvolved(), hasEntry("key3", "Replaced3"));
	}
}
