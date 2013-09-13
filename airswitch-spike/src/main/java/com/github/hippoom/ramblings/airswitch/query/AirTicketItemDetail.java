package com.github.hippoom.ramblings.airswitch.query;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.ToString;

import com.github.hippoom.ramblings.airswitch.query.AirTicketItemDetail.AirTicketItemDetailPk;

@Entity
@Table(name = "t_airswitch_query_ticket_item")
@IdClass(AirTicketItemDetailPk.class)
@ToString
public class AirTicketItemDetail {
	@Id
	public Long ticket_id;
	@Id
	public int rph;

	@Column
	public double fare;

	@SuppressWarnings("serial")
	@NoArgsConstructor
	public static class AirTicketItemDetailPk implements Serializable {
		public Long ticket_id;
		public int rph;
	}
}
