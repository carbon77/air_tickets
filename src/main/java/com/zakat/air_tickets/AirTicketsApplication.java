package com.zakat.air_tickets;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AirTicketsApplication {
    private static final boolean IS_DATA_GENERATED = true;

    public static void main(String[] args) {
        SpringApplication.run(AirTicketsApplication.class, args);
    }

    @Bean
    Faker faker() {
        return new Faker();
    }

    @Bean
    public CommandLineRunner generateData(@Autowired DataGenerator dataGenerator) {
        return args -> {
            if (!IS_DATA_GENERATED) {
                dataGenerator.generateData();
            }
        };
    }

}
