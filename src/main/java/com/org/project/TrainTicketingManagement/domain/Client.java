package com.org.project.TrainTicketingManagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CLIENT")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Client  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3509922552930404946L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENTID", nullable = false)
	private long clientId;
	
	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;
	
	@Column(name = "LASTTNAME", nullable = false)
	private String lastName;
	
	@Column(name = "NIC", nullable = false)
	private String nic;
	
	@Column(name = "ADDRESS", nullable = false)
	private String address;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "CONTACT", nullable = false)
	private String contactNumber;
	
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "ROLE", nullable = false)
	private String role;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registerDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private List<SeatArrangement> seat;
	
	@Transient
	private String rate;

}
