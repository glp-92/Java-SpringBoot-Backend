package com.authregservice.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authregservice.model.pojo.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}