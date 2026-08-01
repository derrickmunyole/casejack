package com.example.casemanagementplatform.cases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name="cases")
@Getter
@NoArgsConstructor
public class Case {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String subject;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private CasePriority priority;

    @Setter
    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    private String tenantId;

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

    public Case(String subject, String description, CasePriority priority, CaseStatus status, String tenantId) {
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.tenantId = tenantId;
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

}
