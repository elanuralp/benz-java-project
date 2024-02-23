package com.benz.javaproject.repository;

import com.benz.javaproject.entity.User;
import com.benz.javaproject.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
