package com.org.project.TrainTicketingManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.Carriages;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.SeatArrangement;
import com.org.project.TrainTicketingManagement.domain.Tickets;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;

@Repository
public interface TrainSeatArrangementRepository extends JpaRepository<SeatArrangement, Long>{

	@Query("SELECT sa FROM SeatArrangement sa WHERE sa.classType = ?1 AND sa.trainSchedule = ?2 AND sa.status=1")
	List<SeatArrangement> findSeatArrangementByCarriagesAndTrainSchedule(Carriages crg, TrainSchedule schedule);

	@Query("SELECT sa FROM SeatArrangement sa WHERE sa.client = ?1 AND sa.trainSchedule = ?2 AND sa.ticket = ?3 AND sa.status=1")
	SeatArrangement findSeatArrangementByClientAndTrainScheduleAndTicket(Client client, TrainSchedule sch, Tickets tkt);

}
