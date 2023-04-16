package com.zakat.air_tickets.repository;

import com.zakat.air_tickets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}