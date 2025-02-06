package com.car.TP2.repository;

import com.car.TP2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * This interface represents a user repository
 */

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}