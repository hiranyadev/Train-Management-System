package com.org.project.TrainTicketingManagement.service;

import java.util.List;

import javax.mail.MessagingException;

import com.org.project.TrainTicketingManagement.domain.BookingDetails;
import com.org.project.TrainTicketingManagement.domain.Carriages;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Payments;
import com.org.project.TrainTicketingManagement.domain.SearchContents;
import com.org.project.TrainTicketingManagement.domain.SeatArrangement;
import com.org.project.TrainTicketingManagement.domain.Station;
import com.org.project.TrainTicketingManagement.domain.Tickets;
import com.org.project.TrainTicketingManagement.domain.Train;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;
import com.org.project.TrainTicketingManagement.dto.ReceiptDetailsDto;

public interface CustomerPanelService {

	List<TrainSchedule> getAllAvailableTrain();
	List<TrainSchedule> getAvailableTrain(SearchContents searchContents);
	List<Station> getAvailableStaions();
	TrainSchedule getTrainScheduleById(Long id);
	List<Carriages> getTrainClassesByTrainId(Train train);
	Station getStationById(Long departurStationId);
	Tickets createTicketBooking(Tickets bookingtickets);
	BookingDetails createBookingDetails(BookingDetails bookingDetails);
	List<Tickets> getAllTicketsByClient(Client client);
	List<BookingDetails> getAllBookingHisotryByClient(Client client);
	Carriages getBookingCabineById(Long cabinid);
	SeatArrangement createSeateArrangement(SeatArrangement seatArrangement);
	List<SeatArrangement> getAllBookedSeatsBycabinAndSchedule(Carriages cabin, TrainSchedule trainSchedule);
	Payments createPayemnts(Payments ticketPayments);
	Payments getPaymentsById(Long paymentId);
	String getPaymentStatusByTicket(Tickets tickets);
	Tickets getTicketById(Long ticketid);
	void removeSeats(SeatArrangement seat);
	Payments getPaymentsByTicket(Tickets tk);
	ReceiptDetailsDto getReceiptDetails(Long ticketid);
	List<TrainTracking> geCuttentLocation(TrainSchedule tsch);
	Client getClientByClientId(int clientId);
	public void send(String fromAddress, String toAddress, String subject, String content) throws MessagingException, MessagingException;
	public void sendFeedback(String fromAddress, String toAddress, String subjectFeedBack, String contentFeedback) throws MessagingException;

}
