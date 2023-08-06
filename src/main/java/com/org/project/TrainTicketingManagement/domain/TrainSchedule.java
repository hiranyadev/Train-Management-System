package com.org.project.TrainTicketingManagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TRAIN_SCHEDULE")
public class TrainSchedule extends Trace  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8413561836224078773L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scheduleId;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Station departure;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Station arrival;
	
	private String departTime;
	private String arrivalTime;	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn (name ="trainId")
	private Train train;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date scheduleDate;
	
	private int status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "trainschedule", cascade = CascadeType.ALL)
	private List<TrainTracking> trainTracking;

}
