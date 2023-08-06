package com.org.project.TrainTicketingManagement.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TRAINCLASS")
public class TrainClasses extends Trace  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5338298163821222914L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainClassid;
	private String code;
	private String description;

}
