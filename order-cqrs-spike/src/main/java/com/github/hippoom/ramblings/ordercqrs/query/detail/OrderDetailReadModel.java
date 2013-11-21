package com.github.hippoom.ramblings.ordercqrs.query.detail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_order_detail")
@DynamicUpdate
@EqualsAndHashCode(of = "trackingId")
@Setter
@Getter
@ToString
public class OrderDetailReadModel {
	@Id
	@Column(name = "tracking_id")
	private String trackingId;
	@Column(name = "bc_name")
	private String bookingContactName;
	@Column(name = "when_placed")
	private Date whenPlaced;
	@Column(name = "total_amount")
	private double totalAmount;
	@Column(name = "total_paid")
	private double totalPaid;
	@Column(name = "balance_status")
	private String balanceStatus;
}
