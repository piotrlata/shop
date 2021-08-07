package com.example.shop.repository;

import com.example.shop.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Integer> {
    Optional<User> findByEmail(String email);
}