package com.github.hippoom.ramblings.credit.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "t_rm_credit_account")
public class CreditAccountReadModel {
	@Id
	@Column(name = "id")
	private Long id;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "effective_start")
	private Date effectiveStart;
	@Temporal(value = TemporalType.DATE)
	@Column(name = "effective_end")
	private Date effectiveEnd;
	@Column(name = "balance")
	private int balance;
}
