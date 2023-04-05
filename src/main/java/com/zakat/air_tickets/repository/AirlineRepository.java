package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
}