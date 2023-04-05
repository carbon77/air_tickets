package com.zakat.air_tickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "airline")
    @JsonIgnore
    @ToString.Exclude
    private List<Flight> flights = new ArrayList<>();

    @Column(name = "name")
    private String name;

}