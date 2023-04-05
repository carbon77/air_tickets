package com.zakat.air_tickets;

import com.github.javafaker.Faker;
import com.zakat.air_tickets.entity.Airline;
import com.zakat.air_tickets.entity.Flight;
import com.zakat.air_tickets.repository.AirlineRepository;
import com.zakat.air_tickets.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DataGenerator {
    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;
    private final Faker faker;

    public void generateData() {
        System.out.println("Generating...");
        for (int i = 0; i < 10; i++) {
            Airline airline = generateAirline();
            int nFlights = faker.number().numberBetween(10, 20);

            for (int j = 0; j < nFlights; j++) {
                Flight flight = generateFlight(airline);
            }
        }
        System.out.println("Data has been generated!");
    }

    @Transactional
    public Airline generateAirline() {
        Airline airline = new Airline();
        airline.setName(faker.company().name());

        return airlineRepository.save(airline);
    }

    @Transactional
    public Flight generateFlight(Airline airline) {
        Flight flight = new Flight();
        flight.setAirline(airline);
        flight.setArrivalCity(faker.address().city());
        flight.setDepartureCity(faker.address().city());

        Date departureDate = faker.date().past(30, TimeUnit.DAYS);
        Date arrivalDate = faker.date().future(1, TimeUnit.DAYS, departureDate);

        Timestamp departure = new Timestamp(departureDate.getTime());
        Timestamp arrival = new Timestamp(arrivalDate.getTime());

        flight.setDepartureTime(departure);
        flight.setArrivalTime(arrival);
        flight.setPrice(faker.number().randomDouble(2, 1000, 10000));

        return flightRepository.save(flight);
    }
}
