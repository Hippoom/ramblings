package com.github.hippoom.ramblings.ordercqrs.query.daily;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "t_daily_order_count_entry")
@IdClass(com.github.hippoom.ramblings.ordercqrs.query.daily.DailyOrderCountEntryReadModel.PK.class)
@Getter
@Setter
@EqualsAndHashCode(of = { "today", "trackingId" })
@ToString
public class DailyOrderCountEntryReadModel {
	@Id
	@Column(name = "date_placed")
	private String today;
	@Id
	@Column(name = "tracking_id")
	private String trackingId;

	@Data
	public static class PK implements Serializable {

		private static final long serialVersionUID = 1L;

		private String today;
		private String trackingId;
	}
}
