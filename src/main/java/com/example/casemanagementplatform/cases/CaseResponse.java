package com.example.casemanagementplatform.cases;

import java.time.LocalDateTime;

public class CaseResponse {
    private Long id;
    private String subject;
    private String description;
    private Case.CaseStatus status;
    private Case.CasePriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CaseResponse(Long id, String subject, String description, Case.CaseStatus status, Case.CasePriority priority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Case.CaseStatus getStatus() {
        return status;
    }

    public Case.CasePriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static CaseResponse fromEntity(Case c) {
        return new CaseResponse(
                c.getId(),
                c.getSubject(),
                c.getDescription(),
                c.getStatus(),
                c.getPriority(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

}
