package com.github.hippoom.ramblings.hotelcatalog;

import java.util.Arrays;
import java.util.List;

public class RoomStay {
	private final String hotel;
	private final List<RoomRate> roomRates;

	public RoomStay(String hotel, RoomRate... roomRates) {
		this.hotel = hotel;
		this.roomRates = Arrays.asList(roomRates);
	}

	public double lowestAverageAmountBeforeTax() {
		if (roomRates().size() == 0) {
			return 0;
		}
		double temp = roomRates().get(0).averageAmountBeforeTax();
		for (int i = 1; i < roomRates().size(); i++) {
			final double averageAmountBeforeTax = roomRates().get(i)
					.averageAmountBeforeTax();
			if (temp < averageAmountBeforeTax) {
				continue;
			} else {
				temp = averageAmountBeforeTax;
			}
		}
		return temp;
	}

	private List<RoomRate> roomRates() {
		return roomRates;
	}

}
