package com.org.project.TrainTicketingManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinDetails {
	
	public Long carriageId;
	public String trainname;
	public String cabinclass;
	public String carriageno;

}
