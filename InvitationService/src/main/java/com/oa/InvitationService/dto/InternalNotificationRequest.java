package com.oa.InvitationService.dto;

public record InternalNotificationRequest(Long projectId, Long roleId, String title, String message) {
}
