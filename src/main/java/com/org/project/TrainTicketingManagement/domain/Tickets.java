package com.org.project.TrainTicketingManagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TICKETS")
public class Tickets extends Trace  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6098110901985344725L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;
	private int numOfSeats;
	private double price;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLIENTID", nullable=false)
	private Client client;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date departureDate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Station departureStation;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Station destinationStation;
	
	@ManyToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="DEPART_TRAINS", 
				joinColumns= {@JoinColumn(name="ticketId", referencedColumnName="ticketId")}, 
				inverseJoinColumns= {@JoinColumn(name="DEPART_TRAIN_ID",referencedColumnName="trainId")}) 
	private List<Train> departTrains;
	
	private boolean isCancelled;
	private String cancelationStatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cancelDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ticketDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	private SeatArrangement seat;
	
	private String ticketNumber;
	

}
