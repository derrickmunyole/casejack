package com.example.casemanagementplatform.cases;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CaseResponse {
    private Long id;
    private String subject;
    private String description;
    private Case.CaseStatus status;
    private Case.CasePriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
