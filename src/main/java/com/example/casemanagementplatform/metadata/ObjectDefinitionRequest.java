package com.example.casemanagementplatform.metadata;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectDefinitionRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}
