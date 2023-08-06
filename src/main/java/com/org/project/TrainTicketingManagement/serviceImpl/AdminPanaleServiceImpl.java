package com.org.project.TrainTicketingManagement.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.project.TrainTicketingManagement.domain.BookingDetails;
import com.org.project.TrainTicketingManagement.domain.Carriages;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Station;
import com.org.project.TrainTicketingManagement.domain.Train;
import com.org.project.TrainTicketingManagement.domain.TrainClasses;
import com.org.project.TrainTicketingManagement.domain.TrainLocations;
import com.org.project.TrainTicketingManagement.domain.TrainLongPriceses;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;
import com.org.project.TrainTicketingManagement.dto.ClientBookingDetailsDto;
import com.org.project.TrainTicketingManagement.repository.BookingDetailsRepository;
import com.org.project.TrainTicketingManagement.repository.ClientRepository;
import com.org.project.TrainTicketingManagement.repository.CustomerPanelRepository;
import com.org.project.TrainTicketingManagement.repository.StationRepository;
import com.org.project.TrainTicketingManagement.repository.TicketRepository;
import com.org.project.TrainTicketingManagement.repository.TrainCarriagesRepository;
import com.org.project.TrainTicketingManagement.repository.TrainClassesRepository;
import com.org.project.TrainTicketingManagement.repository.TrainLocationRepository;
import com.org.project.TrainTicketingManagement.repository.TrainLongPricesesRepository;
import com.org.project.TrainTicketingManagement.repository.TrainRepository;
import com.org.project.TrainTicketingManagement.repository.TrainTrackingRepository;
import com.org.project.TrainTicketingManagement.service.AdminService;

@Service
public class AdminPanaleServiceImpl implements AdminService{
	
	@Autowired
	StationRepository stationRepository;
	
	@Autowired
	TrainRepository trainRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	TrainClassesRepository trainClassesRepository;
	
	@Autowired
	TrainCarriagesRepository trainCarriagesRepository;
	
	@Autowired
	CustomerPanelRepository customerPanelRepository;
	
	@Autowired
	TrainLongPricesesRepository trainLongPricesesRepository;
	
	@Autowired
	TrainTrackingRepository trainTrackingRepository;
	
	@Autowired
	TrainLocationRepository trainLocationRepository;
	
	@Autowired
	BookingDetailsRepository bookingDetailsRepository;
	
	@Autowired
	TicketRepository ticketRepository;

	@Override
	public Station saveStation(Station station) {
		return stationRepository.save(station);
	}

	@Override
	public List<Station> getAllStations() {
		return stationRepository.findAll();
	}

	@Override
	public void deleteStationById(Long id) {
		//stationRepository.deleteStationById(id);	
		stationRepository.deleteById(id);
	}

	@Override
	public List<Train> getAllTrains() {
		return trainRepository.findAll();
	}

	@Override
	public Train saveTrain(Train train) {
		return trainRepository.save(train);		
	}

	@Override
	public void deleteTrainById(Long id) {
		trainRepository.deleteById(id);		
	}

	@Override
	public List<Client> getAllClient() {
		return clientRepository.findAll();
	}

	@Override
	public List<TrainClasses> getAllTrainClasses() {
		return trainClassesRepository.findAll();
	}

	@Override
	public TrainClasses saveTrainClass(TrainClasses trainClasses) {
		return trainClassesRepository.save(trainClasses);
	}

	@Override
	public void deleteTrainClassById(Long id) {
		trainClassesRepository.deleteById(id);
	}

	@Override
	public List<Carriages> getAllTrainCabins() {
		return trainCarriagesRepository.findAll();
	}

	@Override
	public Carriages saveTrainCabin(Carriages trainCabins) {
		return trainCarriagesRepository.save(trainCabins);
	}

	@Override
	public void deleteTrainCabinById(Long id) {
		trainCarriagesRepository.deleteById(id);
	}

	@Override
	public List<TrainSchedule> getAllSchedulesAfterDate(Date date) {
		return customerPanelRepository.findTrainScheduleByScheduleDateAndStatus(date);
	}

	@Override
	public TrainSchedule saveTrainSchedule(TrainSchedule trainSchedule) {
		return customerPanelRepository.save(trainSchedule);
	}

	@Override
	public void deleteScheduleById(Long id) {
		customerPanelRepository.deleteById(id);
	}

	@Override
	public List<TrainLongPriceses> getAllPricess() {
		return trainLongPricesesRepository.findAll();
	}

	@Override
	public TrainLongPriceses saveJourneyPrices(TrainLongPriceses trainLongPriceses) {
		return trainLongPricesesRepository.save(trainLongPriceses);
	}

	@Override
	public List<TrainLocations> getAllTrainLocations() {
		return trainLocationRepository.findAll();
	}
	
	@Override
	public TrainLocations saveTrainLocations(TrainLocations trainLocations) {
		return trainLocationRepository.save(trainLocations);
	}

	@Override
	public List<TrainTracking> getCurruntLocationBySchedule(TrainSchedule tSchedule, Date date) {
		return trainTrackingRepository.findTrainTrackingByTrainSchedule(tSchedule);
	}

	@Override
	public TrainTracking saveTrainTracking(TrainTracking trainTracking) {
		return trainTrackingRepository.save(trainTracking);
	}
	
	@Override
	public List<Client> getClientbyNic(String keyword) {
		return clientRepository.getclientByNic(keyword);
	}

	@Override
	public List<BookingDetails> getAllBookingDetailsClientId(long clientId) {
		return bookingDetailsRepository.getAllBookingDetailsClientId(clientId);
	}
	
	@Override
	public ClientBookingDetailsDto getCalClientRate(long clientId) {
		return bookingDetailsRepository.getCalClientRate(clientId);
	}
	
	@Override
	public List<BookingDetails> getBookingDetails(Date fromDate, Date tDate) {
		return bookingDetailsRepository.getBookingDetails(fromDate, tDate);
	}

	@Override
	public List<TrainSchedule> getAllSchedulesDateBetween(Date fromDate, Date toDate) {
		return customerPanelRepository.findByDateBetween(fromDate, toDate);
	}

	@Override
	public List<Object[]> getTicketDetails(Date fromDate, Date toDate) {
		return ticketRepository.getTicketDetails(fromDate, toDate);
	}

	@Override
	public int getTicketCount(Date ticketDate) {
		return ticketRepository.getTicketCount(ticketDate);
	}

	@Override
	public List<Client> getAllPassenger() {
		return clientRepository.getPassengerDetails();
	}

}
