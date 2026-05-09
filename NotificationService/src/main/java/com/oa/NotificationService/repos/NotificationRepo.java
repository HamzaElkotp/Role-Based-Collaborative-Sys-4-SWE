package com.oa.NotificationService.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oa.NotificationService.entities.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByProjectIdAndRoleId(Long projectId, Long roleId);
}
