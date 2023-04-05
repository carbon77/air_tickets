package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}