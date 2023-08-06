package com.org.project.TrainTicketingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailsDto {
	
	private String clientname;
	private String contact;
	private String email;
	private String nic;
	private String description;
	private String totalamount;
	private String bkdate;
	private String fstation;
	private String tstation;
	private String seats;
	private String printingdate;
	private String ticketnumber;
	private Long ticketId;
	private String paystatus;
}
