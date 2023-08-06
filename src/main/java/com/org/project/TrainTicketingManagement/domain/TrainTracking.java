package com.org.project.TrainTicketingManagement.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TRAINTRACK")
public class TrainTracking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trackingId;
	private Date trackingDate;
	
	@JsonIgnore
	@ManyToOne
	private TrainSchedule trainschedule;
	
	@JsonIgnore
	@OneToOne
	private TrainLocations location;


}
