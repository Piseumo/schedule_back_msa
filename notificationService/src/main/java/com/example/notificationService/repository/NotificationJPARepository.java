package com.example.notificationService.repository;

import com.example.notificationService.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJPARepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverAndReadYn(String receiver, char readYn);
}
