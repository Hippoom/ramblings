package com.github.hippoom.ramblings.ordercqrs.domain;

import lombok.Getter;

public enum BalanceStatus {

	BALANCED("1"), UNBALANCED("0");
	@Getter
	private String value;

	BalanceStatus(String code) {
		this.value = code;
	}
}