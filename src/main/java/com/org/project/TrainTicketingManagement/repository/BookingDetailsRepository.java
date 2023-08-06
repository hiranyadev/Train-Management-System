package com.org.project.TrainTicketingManagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.BookingDetails;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Tickets;
import com.org.project.TrainTicketingManagement.dto.ClientBookingDetailsDto;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long>{
	
	@Query("SELECT bkn FROM BookingDetails bkn WHERE bkn.client = ?1")
	List<BookingDetails> findBookingDetailsByClient(Client client);

	@Query("SELECT bkn FROM BookingDetails bkn WHERE bkn.ticket = ?1")
	BookingDetails findBookingDetailsByTicket(Tickets tkt);
	
	@Query(value="SELECT * FROM BOOKINGDETAILS bd "
		+ "INNER JOIN CLIENT c ON bd.clientid = c.clientid "
		+ "INNER JOIN TICKETS t ON bd.ticket_ticket_id = t.ticket_id "
		+ "INNER JOIN STATION s ON t.departure_station_stationid = s.stationid "
		+ "WHERE c.clientid = ? ", nativeQuery = true)	
	public List<BookingDetails> getAllBookingDetailsClientId(long clientId);
	
	@Query(value="SELECT * FROM BOOKINGDETAILS bd "
			+ "INNER JOIN CLIENT c ON bd.clientid = c.clientid "
			+ "INNER JOIN TICKETS t ON bd.ticket_ticket_id = t.ticket_id "
			+ "WHERE c.clientid = ? ", nativeQuery = true)
	public List<BookingDetails> getBookingDetailsByClientId(String clientId);

	@Query( nativeQuery = true,name="BookingDetails.getCalClientRate")
	public ClientBookingDetailsDto getCalClientRate(long clientId);
	
	@Query("SELECT bkn FROM BookingDetails bkn INNER JOIN Tickets tkt ON tkt.ticketId = bkn.ticket.ticketId "
			+ "WHERE bkn.bookingDate >= ?1 AND bkn.bookingDate <= ?2 AND tkt.cancelationStatus = 'A'")
	List<BookingDetails> getBookingDetails(Date fromDate, Date toDate);

}
