package com.campusconnectapp.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "accused_user_id", nullable = true)
    private User accusedUser;

    @ManyToOne
    @JoinColumn(name = "accused_deleted_account_id", nullable = true)
    private DeletedAccount accusedDeletedAccount;

    @Column(name = "report_type", nullable = false, length = 50)
    private String reportType;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToOne
    @JoinColumn(
            name = "conversation_id",
            nullable = false,
            unique = true
    )
    private Conversation conversation;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(
            name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // No-argument constructor
    public Report() {
    }

    // All-argument constructor
    public Report(
            Long id,
            User user,
            User accusedUser,
            DeletedAccount accusedDeletedAccount,
            String reportType,
            String description,
            Conversation conversation,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.user = user;
        this.accusedUser = accusedUser;
        this.accusedDeletedAccount = accusedDeletedAccount;
        this.reportType = reportType;
        this.description = description;
        this.conversation = conversation;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAccusedUser() {
        return accusedUser;
    }

    public void setAccusedUser(User accusedUser) {
        this.accusedUser = accusedUser;
    }

    public DeletedAccount getAccusedDeletedAccount() {
        return accusedDeletedAccount;
    }

    public void setAccusedDeletedAccount(
            DeletedAccount accusedDeletedAccount
    ) {
        this.accusedDeletedAccount = accusedDeletedAccount;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
