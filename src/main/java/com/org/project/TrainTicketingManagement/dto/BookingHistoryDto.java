package com.org.project.TrainTicketingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistoryDto {
	
	private String ticketNo;
	private String bookingDate;
	private String depStation;
	private String desStation;
	private String totalPrice;
	private String ticketStatus;
	private String payStatus;
	private Long ticketId;

}
