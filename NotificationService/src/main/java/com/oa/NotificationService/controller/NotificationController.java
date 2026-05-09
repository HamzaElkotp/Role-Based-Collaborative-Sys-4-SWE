package com.oa.NotificationService.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.NotificationService.dto.NotificationDto;
import com.oa.NotificationService.entities.Notification;
import com.oa.NotificationService.services.NotificationService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor // Using this instead of @Autowired for clean constructor injection
public class NotificationController {

    private final NotificationService notificationService;

    // Route 1: Get EVERYTHING
    // URL: GET http://localhost:8080/notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // Route 2: Get by Project and Role using Path Variables
    // URL: GET http://localhost:8080/notifications/project/1/role/5
    @GetMapping("/project/{projectId}/role/{roleId}")
    public ResponseEntity<List<Notification>> getByProjectAndRole(
            @PathVariable Long projectId, 
            @PathVariable Long roleId) {
        
        return ResponseEntity.ok(notificationService.getNotificationsByProjectAndRole(projectId, roleId));
    }

    @PostMapping("/internal/notifications")
    public ResponseEntity<Notification> createInternalNotification(@Valid @RequestBody NotificationDto dto) {
        return ResponseEntity.ok(notificationService.createNotification(dto));
    }
}
