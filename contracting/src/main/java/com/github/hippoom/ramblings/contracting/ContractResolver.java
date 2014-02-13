package com.github.hippoom.ramblings.contracting;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContractResolver {
	private String text;
	private Map<String, String> placeHolders;

	public ContractResolver(String text, Map<String, String> placeHolders) {
		this.text = text;
		this.placeHolders = placeHolders;

	}

	public Map<String, String> placeHolders() {
		return placeHolders;
	}

	public String resolved() {
		return resolved(text);
	}
	
	private String resolved(String text) {
		String temp = text;
		Pattern pattern = Pattern.compile("[${](.*?)[}]");
		Matcher matcher = pattern.matcher(temp);
		while (matcher.find()) {
			String value = placeHolders.get(matcher.group(1).substring(1));
			if (value != null) {
				temp = temp.replace(matcher.group(0), resolved(value));
			}
		}
		return temp;
	}

}
