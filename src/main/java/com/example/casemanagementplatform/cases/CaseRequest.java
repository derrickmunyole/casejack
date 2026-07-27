package com.example.casemanagementplatform.cases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CaseRequest {
    @NotBlank(message = "Subject is required")
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Status is required")
    private Case.CaseStatus status;

    public Case.CaseStatus getStatus() {
        return status;
    }

    public void setStatus(Case.CaseStatus status) {
        this.status = status;
    }

    @NotNull(message="Priority is required")
    private Case.CasePriority priority;

    public Case.CasePriority getPriority() {
        return priority;
    }

    public void setPriority(Case.CasePriority priority) {
        this.priority = priority;
    }

}
