package com.org.project.TrainTicketingManagement.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="JOURNEYPRICESES")
public class TrainLongPriceses {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long longPriceId;
	
	@OneToOne(cascade = CascadeType.ALL)
	public Station fromStation;
	
	@OneToOne(cascade = CascadeType.ALL)
	public Station toStation;
	
	@OneToOne(cascade = CascadeType.ALL)
	private TrainClasses trainclass;
	
	public double price;
	public double longForJourney;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date effectiveDate;

}
