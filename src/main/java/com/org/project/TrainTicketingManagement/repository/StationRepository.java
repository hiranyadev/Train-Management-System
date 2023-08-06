package com.org.project.TrainTicketingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long>{
	
}
