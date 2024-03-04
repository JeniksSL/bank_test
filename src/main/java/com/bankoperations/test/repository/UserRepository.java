package com.bankoperations.test.repository;

import com.bankoperations.test.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);
    boolean existsByLogin(String login);
}
