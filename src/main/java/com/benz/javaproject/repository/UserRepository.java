package com.benz.javaproject.repository;

import com.benz.javaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username); //we need to find user by email
}
