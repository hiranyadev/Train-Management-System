package com.org.project.TrainTicketingManagement.dto;

import java.util.Objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDetailsDto {
	
	private int clientId;
	private int bookdSeats;
	private String departurStation;
	private String destinationStation;
	
	private String bookingDate;
	
	private String firstName;
	private String lastName;
	private Data returnDate;
	private Data depatureDate;
	private String returnDeparturStation;
	private String returnDstinationStation;
	private double price;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientDetailsDto clientDetailsDto = (ClientDetailsDto) o;
        return clientId == clientDetailsDto.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientId);
    }


}
