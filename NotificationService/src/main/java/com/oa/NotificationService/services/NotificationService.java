package com.oa.NotificationService.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.NotificationService.entities.Notification;
import com.oa.NotificationService.repos.NotificationRepo;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepo notificationRepo;

    /**
     * 1. Get notifications by ProjectID & RoleID
     */
    public List<Notification> getNotificationsByProjectAndRole(Long projectId, Long roleId) {
        return notificationRepo.findByProjectIdAndRoleId(projectId, roleId);
    }

    /**
     * 2. Get all notifications
     */
    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }
}