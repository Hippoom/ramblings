package com.github.hippoom.ramblings.promotion;

import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

public class CouponCaculatorInstance {

	private String identifier;
	private String script;
	private Map<String, String> params = new HashMap<String, String>();

	public CouponCaculatorInstance(String identifier) {
		this.identifier = identifier;
	}

	public CouponCaculatorInstance scriptIs(String script) {
		this.script = script;
		return this;
	}

	public CouponCaculatorInstance addParam(String key, double value) {
		this.params.put(key, String.valueOf(value));
		return this;
	}

	public String identifier() {
		return identifier;
	}

	public double calculateWith(double totalAmountBefore) {
		GroovyShell shell = new GroovyShell();
		Script script = shell.parse(resolvedScript(totalAmountBefore));
		return ((Number) script.run()).doubleValue();
	}

	private String resolvedScript(double totalAmountBefore) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("totalAmount", String.valueOf(totalAmountBefore));
		params.putAll(this.params);
		return new ScriptResolver(this.script, params).resolved();
	}

}
