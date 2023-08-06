package com.org.project.TrainTicketingManagement.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PAYMENTS")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Payments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymetId;
	private double paymentAmount;
	private Date payDate;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ticketid", nullable=false)
	private Tickets tickets;
	
	private String currency;
	private String method;
	private String intent;
	private String description;
	private String paymentStatus;

}
