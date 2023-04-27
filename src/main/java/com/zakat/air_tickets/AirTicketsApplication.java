package com.zakat.air_tickets;

import com.github.javafaker.Faker;
import com.zakat.air_tickets.entity.User;
import com.zakat.air_tickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AirTicketsApplication {
    private static final boolean IS_DATA_GENERATED = false;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.email}")
    private String adminEmail;

    public static void main(String[] args) {
        SpringApplication.run(AirTicketsApplication.class, args);
    }

    @Bean
    Faker faker() {
        return new Faker();
    }

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .role("ADMIN")
                    .build();

            userRepository.save(user);
        };
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
