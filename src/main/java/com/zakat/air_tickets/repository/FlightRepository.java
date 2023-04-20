package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findAllByDepartureCityContainsAndArrivalCityContainsAndDepartureTimeGreaterThanEqualOrArrivalTimeLessThanEqual(
            String departureCity,
            String arrivalCity,
            Timestamp departureTime,
            Timestamp arrivalTime
    );

    default List<Flight> search(
            String departureCity,
            String arrivalCity,
            Timestamp departureTime,
            Timestamp arrivalTime
    ) {
        return findAllByDepartureCityContainsAndArrivalCityContainsAndDepartureTimeGreaterThanEqualOrArrivalTimeLessThanEqual(
                departureCity,
                arrivalCity,
                departureTime,
                arrivalTime
        );
    }
}