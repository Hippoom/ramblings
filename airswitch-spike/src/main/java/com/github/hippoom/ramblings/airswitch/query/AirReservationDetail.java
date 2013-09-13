package com.github.hippoom.ramblings.airswitch.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_airswitch_query_reservation")
@DynamicUpdate
@ToString
public class AirReservationDetail {
	@Id
	public Long id;
	@Column
	public String status;
	@Column
	public String booking_contact;
	@Column
	public String itinerary;
	@Column
	public double total_amount;
}
