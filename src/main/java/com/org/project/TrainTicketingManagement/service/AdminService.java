package com.org.project.TrainTicketingManagement.service;

import java.util.Date;
import java.util.List;

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

public interface AdminService {

	Station saveStation(Station station);

	List<Station> getAllStations();

	void deleteStationById(Long id);

	List<Train> getAllTrains();

	Train saveTrain(Train train);

	void deleteTrainById(Long id);

	List<Client> getAllClient();

	List<TrainClasses> getAllTrainClasses();

	TrainClasses saveTrainClass(TrainClasses trainClasses);

	void deleteTrainClassById(Long id);

	List<Carriages> getAllTrainCabins();

	Carriages saveTrainCabin(Carriages trainCabins);

	void deleteTrainCabinById(Long id);

	List<TrainSchedule> getAllSchedulesAfterDate(Date date);

	TrainSchedule saveTrainSchedule(TrainSchedule trainSchedule);

	void deleteScheduleById(Long id);

	List<TrainLongPriceses> getAllPricess();

	TrainLongPriceses saveJourneyPrices(TrainLongPriceses trainLongPriceses);

	List<TrainLocations> getAllTrainLocations();

	TrainLocations saveTrainLocations(TrainLocations trainLocations);

	List<TrainTracking> getCurruntLocationBySchedule(TrainSchedule tSchedule, Date date);

	TrainTracking saveTrainTracking(TrainTracking trainTracking);
	
	List<Client> getClientbyNic(String keyword);

	List<BookingDetails> getAllBookingDetailsClientId(long clientId);

	ClientBookingDetailsDto getCalClientRate(long clientId);
	
	List<BookingDetails> getBookingDetails(Date fromDate, Date tDate);

	List<TrainSchedule> getAllSchedulesDateBetween(Date fromDate, Date toDate);

	List<Object[]> getTicketDetails(Date fDate, Date tDate);

	int getTicketCount(Date ticketDate);

	List<Client> getAllPassenger();

}
