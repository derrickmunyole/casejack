package com.example.casemanagementplatform.metadata;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ObjectDefinitionResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static ObjectDefinitionResponse fromEntity(ObjectDefinition o) {
        return new ObjectDefinitionResponse(
                o.getId(),
                o.getName(),
                o.getDescription(),
                o.getCreatedAt(),
                o.getUpdatedAt());
    }
}
