package com.org.project.TrainTicketingManagement.dto;

import com.org.project.TrainTicketingManagement.domain.TrainSchedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSearchDetails {
	
	private String trainName;
	private int availbleSeats;
	private TrainSchedule trainSchedule;
	private String departurStation;
	private String destinationStation;
	private String trainNumber;
	private String trainClass;
	private String bookingDate;
	

}
