package com.example.casemanagementplatform.cases;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="cases")
public class Case {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private CasePriority priority;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum CasePriority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public enum CaseStatus {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }

    public Case() {
    }

    public Case(String subject, String description, CasePriority priority, CaseStatus status) {
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public CasePriority getPriority() {
        return priority;
    }

    public void setPriority(CasePriority priority) {
        this.priority = priority;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
