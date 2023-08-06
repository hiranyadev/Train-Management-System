package com.org.project.TrainTicketingManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ConstructorResult;

import javax.persistence.ColumnResult;
import com.org.project.TrainTicketingManagement.dto.ClientBookingDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity

@SqlResultSetMapping(
	    name = "MyDTOMapping",
	    classes = @ConstructorResult(
	        targetClass = ClientBookingDetailsDto.class,
	        columns = {
	            @ColumnResult(name = "numberoftickets", type = Double.class),
	            @ColumnResult(name = "sumOfPrice", type = Double.class)
	        }
	    )
	)
	@NamedNativeQuery(
	    name = "BookingDetails.getCalClientRate",
	    query = "SELECT COUNT(ticket_id) AS numberoftickets, SUM(price) AS sumOfPrice FROM TICKETS t WHERE t.clientid = ? ;",
	    resultSetMapping = "MyDTOMapping"
	)
@Table(name="BOOKINGDETAILS")
public class BookingDetails  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -860962383538220704L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLIENTID", nullable=false)
	private Client client;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TrainSchedule shedule;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Tickets ticket;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date bookingDate;
	

}
