package com.example.casemanagementplatform.metadata;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldDefinitionRequest {

    private Long objectDefinitionId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "Field type is required")
    private FieldDefinition.FieldType fieldType;

    private boolean required;

    private String defaultValue;

    private List<String> picklistValues;


}
