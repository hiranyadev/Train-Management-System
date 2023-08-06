package com.org.project.TrainTicketingManagement.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchContents {
	
	private Station departureStation;
	private Station destinationStation;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date departureDate;

}
