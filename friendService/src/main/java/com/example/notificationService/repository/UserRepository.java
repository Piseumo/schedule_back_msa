package com.example.notificationService.repository;

import com.example.notificationService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByUserNameContainingAndIdxNotIn(String userName, List<Long> excludedIds);
}
