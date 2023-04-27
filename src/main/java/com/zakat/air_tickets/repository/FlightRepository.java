package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(
            "SELECT f.departureCity FROM Flight f"
    )
    TreeSet<String> getAllDepartureCities();

    @Query(
            "SELECT f.arrivalCity FROM Flight f"
    )
    TreeSet<String> getAllArrivalCities();

    List<Flight> findAllByDepartureCityContainsAndArrivalCityContainsAndDepartureTimeAfterAndArrivalTimeBefore(
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
        return findAllByDepartureCityContainsAndArrivalCityContainsAndDepartureTimeAfterAndArrivalTimeBefore(
                departureCity,
                arrivalCity,
                departureTime,
                arrivalTime
        );
    }
}