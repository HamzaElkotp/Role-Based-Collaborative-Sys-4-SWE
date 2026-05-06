package com.oa.InvitationService.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Invitee Role ID is required")
    @Column(nullable = false)
    private Long inviteeRoleId;

    @NotNull(message = "Project ID is required")
    @Column(nullable = false)
    private Long projectId;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "PENDING|ACCEPTED|CANCELED|EXPIRED", 
             message = "Status must be PENDING, ACCEPTED, CANCELED, or EXPIRED")
    private String status = "PENDING";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resentAt;

    private String invitationLink;
}