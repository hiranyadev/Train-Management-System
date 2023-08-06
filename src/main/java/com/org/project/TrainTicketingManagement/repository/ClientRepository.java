package com.org.project.TrainTicketingManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	public boolean existsByEmail(String email);

	public Client findByUsername(String username);

	public Client findByEmail(String username);
	
	@Query(value="SELECT * FROM CLIENT cl WHERE cl.nic like %:nic%", nativeQuery = true)
	public List<Client> getclientByNic(String nic);
	
	@Query(value="SELECT * FROM CLIENT cl WHERE cl.clientId like %:clientId% ", nativeQuery = true)
	Client getClientByClientId(int clientId);
	
	@Query("SELECT cl FROM Client cl WHERE cl.role = 'ROLE_USER'")
	public List<Client> getPassengerDetails();

}
