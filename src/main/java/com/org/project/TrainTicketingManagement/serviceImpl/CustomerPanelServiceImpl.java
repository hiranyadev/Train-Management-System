package com.org.project.TrainTicketingManagement.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
import com.org.project.TrainTicketingManagement.repository.BookingDetailsRepository;
import com.org.project.TrainTicketingManagement.repository.ClientRepository;
import com.org.project.TrainTicketingManagement.repository.CustomerPanelRepository;
import com.org.project.TrainTicketingManagement.repository.PaymentsRepository;
import com.org.project.TrainTicketingManagement.repository.StationRepository;
import com.org.project.TrainTicketingManagement.repository.TicketRepository;
import com.org.project.TrainTicketingManagement.repository.TrainCarriagesRepository;
import com.org.project.TrainTicketingManagement.repository.TrainSeatArrangementRepository;
import com.org.project.TrainTicketingManagement.repository.TrainTrackingRepository;
import com.org.project.TrainTicketingManagement.service.CustomerPanelService;

@Service
public class CustomerPanelServiceImpl implements CustomerPanelService{
	
	@Autowired
	public CustomerPanelRepository customerPanelRepository;
	
	@Autowired
	public StationRepository stationRepository;
	
	@Autowired
	public TicketRepository ticketRepository;
	
	@Autowired
	public BookingDetailsRepository bookingDetailsRepository;
	
	@Autowired
	public TrainCarriagesRepository trainCarriagesRepository;
	
	@Autowired
	public TrainSeatArrangementRepository trainSeatArrangementRepository;
	
	@Autowired
	public PaymentsRepository paymentsRepository;
	
	@Autowired
	TrainTrackingRepository trainTrackingRepository;
	
	@Autowired
	public ClientRepository clientRepository;
	
	@Autowired private JavaMailSender javaMailSender;

	@Override
	public List<TrainSchedule> getAvailableTrain(SearchContents searchContents) {
		//return customerPanelRepository.findAll();
		return customerPanelRepository.findTrainScheduleByDepartureAndArrivalAndScheduleDateAndStatus(searchContents.getDepartureStation(), searchContents.getDestinationStation(), searchContents.getDepartureDate());
	}

	@Override
	public List<TrainSchedule> getAllAvailableTrain() {
		return customerPanelRepository.findAll();
	}

	@Override
	public List<Station> getAvailableStaions() {
		return stationRepository.findAll();
	}

	@Override
	public TrainSchedule getTrainScheduleById(Long id) {
		return customerPanelRepository.findById(id).get();
	}

	@Override
	public List<Carriages> getTrainClassesByTrainId(Train train) {
		return null;//carriagesRepository.getTrainClassesByTrainId((int) train.getTrainId());
	}

	@Override
	public Station getStationById(Long departurStationId) {
		return stationRepository.findById(departurStationId).get();
	}

	@Override
	public Tickets createTicketBooking(Tickets bookingtickets) {
		return ticketRepository.save(bookingtickets);
	}
	
	@Override
	public BookingDetails createBookingDetails(BookingDetails bookingDetails) {
		return bookingDetailsRepository.saveAndFlush(bookingDetails);
	}

	@Override
	public List<Tickets> getAllTicketsByClient(Client client) {
		return ticketRepository.findTicketsByClient(client);
	}

	@Override
	public List<BookingDetails> getAllBookingHisotryByClient(Client client) {
		return bookingDetailsRepository.findBookingDetailsByClient(client);
	}

	@Override
	public Carriages getBookingCabineById(Long cabinid) {
		return trainCarriagesRepository.findById(cabinid).get();
	}

	@Override
	public SeatArrangement createSeateArrangement(SeatArrangement seatArrangement) {
		seatArrangement.setStatus(1);
		return trainSeatArrangementRepository.save(seatArrangement);
	}

	@Override
	public List<SeatArrangement> getAllBookedSeatsBycabinAndSchedule(Carriages cabin, TrainSchedule trainSchedule) {
		return trainSeatArrangementRepository.findSeatArrangementByCarriagesAndTrainSchedule(cabin, trainSchedule);
	}

	@Override
	public Payments createPayemnts(Payments ticketPayments) {
		return paymentsRepository.save(ticketPayments);
	}

	@Override
	public Payments getPaymentsById(Long paymentId) {
		return paymentsRepository.findById(paymentId).get();
	}

	@Override
	public String getPaymentStatusByTicket(Tickets tickets) {
		Payments py = paymentsRepository.findPaymentsByTicket(tickets);
		if(py!=null)
			return py.getPaymentStatus();
		return "";
	}

	@Override
	public Tickets getTicketById(Long ticketid) {
		return ticketRepository.findById(ticketid).get();
	}

	@Override
	public void removeSeats(SeatArrangement seat) {
		trainSeatArrangementRepository.save(seat);		
	}

	@Override
	public Payments getPaymentsByTicket(Tickets tk) {
		return paymentsRepository.findPaymentsByTicket(tk);
	}

	@Override
	public ReceiptDetailsDto getReceiptDetails(Long ticketid) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ReceiptDetailsDto data = new ReceiptDetailsDto();
		
		Tickets tkt = ticketRepository.findById(ticketid).get();
		BookingDetails bkd = bookingDetailsRepository.findBookingDetailsByTicket(tkt);
		TrainSchedule sch = bkd.getShedule();
		SeatArrangement seatArrangement = trainSeatArrangementRepository.findSeatArrangementByClientAndTrainScheduleAndTicket(tkt.getClient(), sch, tkt);
		Payments pay = paymentsRepository.findPaymentsByTicket(tkt);
		
		
		data.setClientname(tkt.getClient().getFirstName()+" "+tkt.getClient().getLastName());
		data.setContact(tkt.getClient().getContactNumber());
		data.setEmail(tkt.getClient().getEmail());
		data.setNic(tkt.getClient().getNic());
		data.setDescription("Ticket No: "+tkt.getTicketNumber()+" --- "
							+ "Cabin No: "+seatArrangement.getClassType().getCarriageno()+" --- "
							+ "Cabin Class: "+seatArrangement.getClassType().getTrainclass().getDescription()+" --- "
							+ "Recerved Seats: "+seatArrangement.allSeatsRecerved(seatArrangement)+"--- "
							+ "Train Name: "+sch.getTrain().getTrainName()+" --- "
							+ "Train Number: "+sch.getTrain().getTrainNo());
		data.setTotalamount(String.valueOf(tkt.getPrice()*tkt.getNumOfSeats()));
		data.setBkdate(dateFormat.format(tkt.getDepartureDate()));
		data.setFstation(tkt.getDepartureStation().getStationName());
		data.setTstation(tkt.getDestinationStation().getStationName());
		data.setSeats(String.valueOf(tkt.getNumOfSeats()));
		data.setPrintingdate(dateFormat.format(new Date()));
		data.setTicketnumber(tkt.getTicketNumber());
		data.setTicketId(ticketid);
		
		if("P".equals(pay.getPaymentStatus())) {
			data.setPaystatus("PAYED");
		}else {
			data.setPaystatus("NOT PAYED");
		}
		
		return data;
	}

	@Override
	public List<TrainTracking> geCuttentLocation(TrainSchedule tsch) {
		return trainTrackingRepository.findTrainTrackingByTrainSchedule(tsch);
	}	
	 
	@Override
	public Client getClientByClientId(int clientId) {
		return clientRepository.getClientByClientId(clientId);
	}
	
	@Override
	public void send(String fromAddress, String toAddress, String subject, String content) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(fromAddress);
		mimeMessageHelper.setTo(toAddress);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(content, true);
		mimeMessageHelper.setSentDate(new Date());
		javaMailSender.send(mimeMessage);
		
	}

	@Override
	public void sendFeedback(String fromAddress, String toAddress, String subjectFeedBack, String contentFeedback) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(fromAddress);
		mimeMessageHelper.setTo(toAddress);
		mimeMessageHelper.setSubject(subjectFeedBack);
		mimeMessageHelper.setText(contentFeedback, true);
		mimeMessageHelper.setSentDate(new Date());
		javaMailSender.send(mimeMessage);
	}

}
