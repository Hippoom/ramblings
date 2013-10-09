package com.github.hippoom.ramblings.ordercrqs.domain;

import lombok.Getter;

enum BalanceStatus {

	BALANCED("1"), UNBALANCED("0");
	@Getter
	private String value;

	BalanceStatus(String code) {
		this.value = code;
	}
}