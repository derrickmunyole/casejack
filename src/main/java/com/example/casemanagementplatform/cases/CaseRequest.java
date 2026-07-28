package com.example.casemanagementplatform.cases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseRequest {
    @NotBlank(message = "Subject is required")
    private String subject;

    private String description;

    @NotNull(message = "Status is required")
    private Case.CaseStatus status;

    @NotNull(message="Priority is required")
    private Case.CasePriority priority;
}
