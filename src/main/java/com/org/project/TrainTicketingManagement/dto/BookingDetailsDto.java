package com.org.project.TrainTicketingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDto {
	
	private String trainName;
	private int bookdSeats;
	private String departurStation;
	private String destinationStation;
	private String trainNumber;
	private String trainClass;
	private String bookingDate;
	private String clientName;
	private String nic;
	private Long scheduleId;
	private Long clientId;
	
	private Long departurStationId;
	private Long destinationStationId;
	private Long distancePriceId;
	
	private String firstName;
	private String lastName;
	private Data returnDate;
	private Data depatureDate;
	private String returnDeparturStation;
	private String returnDstinationStation;
	
	private double price;
	private String others;
	
	private Double numberoftickets;
	private Double sumOfPrice;

}
