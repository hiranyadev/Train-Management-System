package com.org.project.TrainTicketingManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.SeatArrangement;

@Repository
public interface SeatArragmentRepository extends JpaRepository<SeatArrangement, Long> {
	
	public List<SeatArrangement> findAll();
	
	@Query(value="SELECT * FROM SEATS_ARRANGEMENT sa INNER JOIN TRAIN_SCHEDULE tc ON sa.train_schedule_schedule_id = tc.schedule_id WHERE sa.clientID = ? AND tc.scheduleDate = ?", nativeQuery = true)
	
	public List<SeatArrangement> getAllArrangementsByDateAndClientId(Long clientId, LocalDate date);
	
	@Query(value="SELECT * FROM SEATS_ARRANGEMENT sa INNER JOIN TRAIN_SCHEDULE tc ON sa.train_schedule_schedule_id = tc.schedule_id WHERE tc.scheduleDate = ?", nativeQuery = true)
	public List<SeatArrangement> getAllArrangementsByDate(LocalDate date);

}
