package com.github.hippoom.ramblings.billing.query;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class GatheringView {
	private String orderId;
	private double total;
	private List<String> receipts;
	private Map<String, Double> paid;
	private Map<String, Double> gathered;

	public GatheringView(String orderId, double total, List<String> receipts,
			Map<String, Double> paid, Map<String, Double> gathered) {
		this.orderId = orderId;
		this.total = total;
		this.receipts = receipts;
		this.paid = paid;
		this.gathered = gathered;
	}

}
