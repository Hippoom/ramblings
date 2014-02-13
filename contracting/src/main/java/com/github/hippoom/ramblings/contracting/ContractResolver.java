package com.github.hippoom.ramblings.contracting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContractResolver {
	private final String origin;
	private final Map<String, String> placeHolders;
	private final String resolved;
	private final List<String> unresolved = new ArrayList<String>();
	private final Map<String, String> involved = new HashMap<String, String>();

	public ContractResolver(String text, Map<String, String> placeHolders) {
		this.origin = text;
		this.placeHolders = placeHolders;
		this.resolved = resolved(origin);

	}

	public Map<String, String> placeHolders() {
		return placeHolders;
	}

	public String resolved() {
		return resolved;
	}

	private String resolved(String text) {
		String temp = text;
		Pattern pattern = Pattern.compile("[${](.*?)[}]");
		Matcher matcher = pattern.matcher(temp);
		while (matcher.find()) {
			String key = matcher.group(1).substring(1);
			String value = placeHolders.get(key);

			if (value == null) {
				if (this.unresolved.contains(key)) {
					// already contains
				} else {
					this.unresolved.add(key);
				}
				this.involved.put(key, value);
			} else {
				String resolved = resolved(value);
				this.involved.put(key, resolved);
				temp = temp.replace(matcher.group(0), resolved);
			}

		}
		return temp;
	}

	public List<String> unresolved() {
		return unresolved;
	}

	public Map<String, String> placeHoldersInvolved() {
		return involved;
	}

}
