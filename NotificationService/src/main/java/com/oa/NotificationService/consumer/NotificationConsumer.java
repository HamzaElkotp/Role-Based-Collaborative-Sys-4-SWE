package com.oa.NotificationService.consumer;

import com.oa.NotificationService.dto.NotificationDto;
import com.oa.NotificationService.entities.Notification;
import com.oa.NotificationService.repos.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepo repository;

    @RabbitListener(queues = "notification_queue")
    public void consumeNotification(NotificationDto dto) {
        if (dto == null || dto.getProjectId() == null) {
            log.error("Received empty or invalid DTO. Discarding message.");
            return; // Exits without throwing an exception, so RabbitMQ considers it "Done"
        }

        try {
            log.info("Received notification from queue: {}", dto);
    
            // Map DTO to Entity
            Notification notification = new Notification();
            notification.setType(dto.getType());
            notification.setRoleId(dto.getRoleId());
            notification.setProjectId(dto.getProjectId());
            notification.setTitle(dto.getTitle());
            notification.setMessage(dto.getMessage());
    
            // Save to DB
            repository.save(notification);
            log.info("Notification saved to database for Project ID: {}", dto.getProjectId());
        } catch (Exception e) {
            log.error("Failed to save notification: {}", e.getMessage());
        }
    }
}