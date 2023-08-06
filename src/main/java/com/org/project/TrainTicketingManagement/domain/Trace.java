package com.org.project.TrainTicketingManagement.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Trace {
		
	private Date lastUpdateDate;	
	private int userId;
	private int recordStatus;

}
