package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Booking;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByUserAndFlight(User user, Flight flight);

    List<Booking> findAllByUser(User user);
}