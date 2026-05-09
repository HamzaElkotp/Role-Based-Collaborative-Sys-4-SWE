package com.oa.InvitationService.client;

import com.oa.InvitationService.dto.InternalNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/internal/notifications")
    void createInternalNotification(@RequestBody InternalNotificationRequest request);
}
