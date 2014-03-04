package com.github.hippoom.ramblings.gordering.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="trackingId")
@ToString
public class OrderView {
	private String trackingId;
	private String memberId;
	private String status;
}
