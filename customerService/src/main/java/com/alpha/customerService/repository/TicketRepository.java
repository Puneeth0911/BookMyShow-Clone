package com.alpha.customerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.customerService.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}