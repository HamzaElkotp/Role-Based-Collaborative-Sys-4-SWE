package com.oa.NotificationService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long type = 1L;

    @Column(nullable = false)
    private Long roleId;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private String title;

    private String message;
}