package com.github.hippoom.ramblings.promotion;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptResolver {
	private final String origin;
	private final Map<String, String> placeHolders;
	private final String resolved;

	public ScriptResolver(String text, Map<String, String> placeHolders) {
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

			if (value != null) {
				temp = temp.replace(matcher.group(0), value);
			}

		}
		return temp;
	}

}
