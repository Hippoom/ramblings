package com.github.hippoom.ramblings.bar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Bar {

	private FlightPrices flightPrices = new FlightPrices();
	private FlightLineVacations flightLineVacations = new FlightLineVacations();
	private VacationLineFlights vacationLineFlights = new VacationLineFlights();
	private VacationFlightBars vacationFlightBars = new VacationFlightBars();
	private VacationBars vacationBars = new VacationBars();

	/**
	 * 建立度假产品和航班的关系
	 * 
	 * @param flightNumber
	 *            航班号
	 * @param date
	 *            度假产品出发日期
	 * @param vacation
	 *            度假产品ID
	 */
	public void assignFlightNumberToVacation(String flightNumber, String date,
			String vacation) {
		flightLineVacations.get(flightNumber, date).add(vacation);
		vacationLineFlights.get(vacation, date).add(flightNumber);
	}

	/**
	 * 更新航班价格
	 * 
	 * @param flightNumber
	 *            航班号
	 * @param date
	 *            航班出发日期
	 * @param price
	 *            价格
	 */
	public void updateFlightPrice(String flightNumber, String date,
			BigDecimal price) {
		flightPrices.get(flightNumber).put(date, price);// 更新价格
		notifyFlightPriceChanged(flightNumber, date, price);

	}

	/**
	 * 作废航班价格
	 * 
	 * @param flightNumber
	 *            航班号
	 * @param date
	 *            航班出发日期
	 */
	public void deprecateFlightPrice(String flightNumber, String date) {
		flightPrices.get(flightNumber).put(date, BigDecimal.ZERO);
		notifyFlightPriceDeprecated(flightNumber, date);
	}

	private void notifyFlightPriceChanged(String flightNumber, String date,
			BigDecimal price) {
		Set<String> vacations = flightLineVacations.get(flightNumber, date);

		for (String vacation : vacations) {
			notifyVacationFlightBarChanged(vacation, date, flightNumber, price,
					false);
		}
	}

	private void notifyFlightPriceDeprecated(String flightNumber, String date) {
		Set<String> vacations = flightLineVacations.get(flightNumber, date);

		for (String vacation : vacations) {
			notifyVacationFlightBarChanged(vacation, date);
		}
	}

	private void notifyVacationFlightBarChanged(String vacation, String date) {
		List<Map<String, Object>> decimal = new ArrayList<Map<String, Object>>();
		for (String flightNumber : vacationLineFlights.get(vacation, date)) {
			Map<String, Object> price = new HashMap<String, Object>();
			price.put("price", flightPrices.get(flightNumber).get(date));
			price.put("flightNumber", flightNumber);
			if (((BigDecimal) price.get("price")).compareTo(BigDecimal.ZERO) != 0) {
				decimal.add(price);
			}
		}
		Collections.sort(decimal, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return ((BigDecimal) o1.get("price")).compareTo((BigDecimal) o2
						.get("price"));
			}

		});
		notifyVacationFlightBarChanged(vacation, date, (String) decimal.get(0)
				.get("flightNumber"), (BigDecimal) decimal.get(0).get("price"),
				true);
	}

	private void notifyVacationFlightBarChanged(String vacation, String date,
			String flightNumber, BigDecimal price, boolean force) {
		Map<String, BigDecimal> flightBars = vacationFlightBars.get(vacation);
		BigDecimal currentBar = flightBars.get(date);
		if (force || currentBar == null || currentBar.compareTo(price) >= 0) {
			// 如果强制更新或当前尚未计算出最低价或是该价格比当前最低价还要低
			flightBars.put(date, price);
			notifyVacationBarChanged(vacation, date, price);
		}
	}

	private void notifyVacationBarChanged(String vacation, String date,
			BigDecimal price) {
		vacationBars.get(vacation).put(date, price);
	}

	public BigDecimal getVacationBar(String vacation, String date) {
		return vacationBars.get(vacation).get(date);
	}

}
