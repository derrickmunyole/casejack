package com.example.casemanagementplatform.metadata;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class FieldDefinitionResponse {

    private final Long id;
    private final Long  objectDefinitionId;
    private final String name;
    private final FieldDefinition.FieldType fieldType;
    private final boolean required;
    private final String defaultValue;
    private final List<String> picklistValues;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static FieldDefinitionResponse fromEntity(FieldDefinition fd) {
        List<String> values = fd.getPicklistValues()
                .stream()
                .map(PicklistValue::getValue)
                .toList();

        return new FieldDefinitionResponse(
                fd.getId(),
                fd.getObjectDefinition().getId(),
                fd.getName(),
                fd.getFieldType(),
                fd.isRequired(),
                fd.getDefaultValue(),
                values,
                fd.getCreatedAt(),
                fd.getUpdatedAt()
        );
    }

}
