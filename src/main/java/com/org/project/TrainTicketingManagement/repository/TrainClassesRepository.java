package com.org.project.TrainTicketingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.project.TrainTicketingManagement.domain.TrainClasses;

@Repository
public interface TrainClassesRepository extends JpaRepository<TrainClasses, Long>{

}
