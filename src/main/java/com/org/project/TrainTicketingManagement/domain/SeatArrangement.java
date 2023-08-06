package com.org.project.TrainTicketingManagement.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SEATS_ARRANGEMENT")
public class SeatArrangement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long seatArrId;
	
	@ElementCollection
	private List<String> seatNo;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Carriages classType;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private TrainSchedule trainSchedule;
	
	@ManyToOne
	private Client client;
	
	@OneToOne
	private Tickets ticket;
	
	private int status;
	
	public String allSeatsRecerved(SeatArrangement seatArrangement) {
		String allreceved = "";		
		for(String s : seatArrangement.seatNo) {
			allreceved+=" "+s;
		}		
		return allreceved;
	}

}
