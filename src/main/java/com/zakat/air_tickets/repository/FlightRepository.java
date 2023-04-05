package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}