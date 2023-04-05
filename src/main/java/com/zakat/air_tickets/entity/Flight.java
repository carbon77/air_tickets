package com.zakat.air_tickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Integer id;

    @Column(name = "departure_city")
    private String departure_city;

    @Column(name = "arrival_city")
    private String arrival_city;

    @Column(name = "departure_time")
    private Date departure_time;

    @Column(name = "arrival_time")
    private Date arrival_time;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @OneToMany(mappedBy = "flight", orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

}