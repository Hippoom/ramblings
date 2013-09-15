package com.github.hippoom.ramblings.airswitch.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.ToString;

@Entity
@Table(name = "t_airswitch_query_ticket")
@DynamicUpdate
@ToString
public class AirTicketDetail {
	@Id
	public Long id;
	@Column
	public Long reservation_id;
	@Column
	public String status;
	@Column
	public double total_amount;
	@Column
	public String tkt_number;
}
