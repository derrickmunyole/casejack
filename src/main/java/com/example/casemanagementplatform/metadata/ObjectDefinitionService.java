package com.example.casemanagementplatform.metadata;


import com.example.casemanagementplatform.common.exceptions.ObjectDefinitionAlreadyExistsException;
import com.example.casemanagementplatform.common.exceptions.ObjectDefinitionNotFoundException;
import com.example.casemanagementplatform.common.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectDefinitionService {
    private final ObjectDefinitionRepository objectDefinitionRepository;

    public ObjectDefinitionService(ObjectDefinitionRepository objectDefinitionRepository) {
        this.objectDefinitionRepository = objectDefinitionRepository;
    }

    public ObjectDefinitionResponse createObjectDefinition(ObjectDefinitionRequest request) {
        String tenantId = TenantContext.getTenantId();

        boolean objectCheck = objectDefinitionRepository.existsByTenantIdAndName(tenantId, request.getName());
        if (objectCheck) {
            throw new ObjectDefinitionAlreadyExistsException("An object named %s already exists".formatted(request.getName()));
        }

        ObjectDefinition newObjectDefinition = new ObjectDefinition(
                request.getName(),
                request.getDescription(),
                tenantId
        );

        ObjectDefinition saved = objectDefinitionRepository.save(newObjectDefinition);
        return ObjectDefinitionResponse.fromEntity(saved);
    }

    public ObjectDefinitionResponse getObjectDefinitionById(Long id) {
        String tenantId = TenantContext.getTenantId();
        ObjectDefinition objectDefinition = objectDefinitionRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ObjectDefinitionNotFoundException("Object definition with id %d not found".formatted(id)));
        return ObjectDefinitionResponse.fromEntity(objectDefinition);
    }

    public List<ObjectDefinitionResponse> getAllObjectDefinitions() {
        String tenantId = TenantContext.getTenantId();
        return objectDefinitionRepository.findByTenantId(tenantId)
                .stream()
                .map(ObjectDefinitionResponse::fromEntity)
                .toList();
    }
}