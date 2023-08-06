package com.org.project.TrainTicketingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.TrainLocations;

@Repository
public interface TrainLocationRepository extends JpaRepository<TrainLocations, Long>{

}
