package com.org.project.TrainTicketingManagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Tickets;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long>{

	@Query("SELECT tkt FROM Tickets tkt WHERE tkt.client = ?1 ")
	List<Tickets> findTicketsByClient(Client client);
	
	@Query("SELECT tkt.ticketDate, SUM(tkt.numOfSeats), SUM(tkt.numOfSeats*tkt.price) FROM Tickets tkt WHERE tkt.ticketDate >= ?1 AND tkt.ticketDate <= ?2 AND tkt.cancelationStatus = 'A' GROUP BY tkt.ticketDate ")
	List<Object[]> getTicketDetails(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate);
	
	@Query("SELECT SUM(tkt.numOfSeats) FROM Tickets tkt WHERE ticketDate = ?1")
	int getTicketCount(Date ticketDate);

}
