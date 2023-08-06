package com.org.project.TrainTicketingManagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.Station;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;

@Repository
public interface CustomerPanelRepository extends JpaRepository<TrainSchedule, Long>{
	
	List<TrainSchedule> findByscheduleDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	
	@Query("SELECT ts FROM TrainSchedule ts WHERE ts.departure = ?1 AND ts.arrival = ?2 AND ts.scheduleDate = ?3 AND ts.status=1")
	List<TrainSchedule> findTrainScheduleByDepartureAndArrivalAndScheduleDateAndStatus(Station departureStation, Station destinationStation, Date departureDate);
	
	@Query("SELECT ts FROM TrainSchedule ts WHERE ts.scheduleDate >= ?1 AND ts.status=1")
	List<TrainSchedule> findTrainScheduleByScheduleDateAndStatus(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

	@Query("SELECT ts FROM TrainSchedule ts WHERE ts.scheduleDate >= ?1 AND ts.scheduleDate <= ?2")
	List<TrainSchedule> findByDateBetween(@DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, Date toDate);
	
}
