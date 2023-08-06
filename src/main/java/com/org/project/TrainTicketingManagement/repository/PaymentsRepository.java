package com.org.project.TrainTicketingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.project.TrainTicketingManagement.domain.Payments;
import com.org.project.TrainTicketingManagement.domain.Tickets;

public interface PaymentsRepository extends JpaRepository<Payments, Long>{

	@Query("SELECT py FROM Payments py WHERE py.tickets = ?1")
	Payments findPaymentsByTicket(Tickets tickets);

}
