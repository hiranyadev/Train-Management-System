package com.org.project.TrainTicketingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientBookingDetailsDto {
	
	public ClientBookingDetailsDto(Double numberoftickets,Double sumOfPrice) {
		this.numberoftickets = numberoftickets != null && numberoftickets != 0 ? numberoftickets : 0; 
		this.sumOfPrice = sumOfPrice != null && sumOfPrice != 0 ? sumOfPrice : 0;
	}
	
	private int bookdSeats;
	private String arrivalStation;
	private String departurStation;
	private String destinationStation;
	private String nic;
	private String bookingDate;
	private int numOfSeats;
	private String firstName;
	private String lastName;
	private String returnDate;
	private String depatureDate;
	private String returnDeparturStation;
	private String returnDstinationStation;
	private double price;
	private Double numberoftickets;
	private Double sumOfPrice;
	private String fullName;
	
}
