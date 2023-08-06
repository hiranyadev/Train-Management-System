package com.org.project.TrainTicketingManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;

@Repository
public interface TrainTrackingRepository extends JpaRepository<TrainTracking, Long>{
	
	@Query("SELECT tk FROM TrainTracking tk WHERE tk.trainschedule = ?1 ORDER BY trackingId DESC")
	List<TrainTracking> findTrainTrackingByTrainSchedule(TrainSchedule tSchedule);

}
