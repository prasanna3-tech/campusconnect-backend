package com.campusconnectapp.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_accounts")
public class DeletedAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_user_id", nullable = false, unique = true)
    private Long originalUserId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "deleted_at", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime deletedAt;

    @Column(name = "reason", length = 500)
    private String reason;

    // No-argument constructor
    public DeletedAccount() {
    }

    // All-argument constructor
    public DeletedAccount(
            Long id,
            Long originalUserId,
            String userName,
            String email,
            String reason
    ) {
        this.id = id;
        this.originalUserId = originalUserId;
        this.userName = userName;
        this.email = email;
        this.reason = reason;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalUserId() {
        return originalUserId;
    }

    public void setOriginalUserId(Long originalUserId) {
        this.originalUserId = originalUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}