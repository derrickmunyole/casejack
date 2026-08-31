package com.example.casemanagementplatform.metadata;

import com.example.casemanagementplatform.common.exceptions.FieldDefinitionAlreadyExistsException;
import com.example.casemanagementplatform.common.exceptions.FieldDefinitionNotFoundException;
import com.example.casemanagementplatform.common.exceptions.ObjectDefinitionNotFoundException;
import com.example.casemanagementplatform.common.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldDefinitionService {
    private final FieldDefinitionRepository fieldDefinitionRepository;
    private final ObjectDefinitionRepository objectDefinitionRepository;

    public FieldDefinitionService(FieldDefinitionRepository fieldDefinitionRepository, ObjectDefinitionRepository objectDefinitionRepository) {
        this.fieldDefinitionRepository = fieldDefinitionRepository;
        this.objectDefinitionRepository = objectDefinitionRepository;
    }

    public FieldDefinitionResponse createFieldDefinition(FieldDefinitionRequest request) {
        String tenantId = TenantContext.getTenantId();

        ObjectDefinition objectDefinition = objectDefinitionRepository
                .findByIdAndTenantId(request.getObjectDefinitionId(), tenantId)
                .orElseThrow(() -> new ObjectDefinitionNotFoundException("Object definition with id %d not found".formatted(request.getObjectDefinitionId())));

        boolean fieldExists = fieldDefinitionRepository
                .existsByObjectDefinitionIdAndNameAndTenantId(objectDefinition.getId(), request.getName(), tenantId);

        if (fieldExists) {
            throw new FieldDefinitionAlreadyExistsException("A field named %s already exists on this object".formatted(request.getName()));
        }

        FieldDefinition newFieldDefinition = new FieldDefinition(
                request.getName(),
                request.getFieldType(),
                objectDefinition,
                request.isRequired(),
                request.getDefaultValue(),
                tenantId
        );

        if (request.getFieldType() == FieldDefinition.FieldType.PICKLIST && request.getPicklistValues() != null) {
            List<PicklistValue> pickListValues = new ArrayList<>();

            int pickListValuePosition = 0;

            for (String value : request.getPicklistValues()) {
                pickListValues.add(new PicklistValue(newFieldDefinition, value, pickListValuePosition));
                pickListValuePosition++;
            }
            newFieldDefinition.getPicklistValues().addAll(pickListValues);
        }

        FieldDefinition saved = fieldDefinitionRepository.save(newFieldDefinition);
        return FieldDefinitionResponse.fromEntity(saved);

    }

    public FieldDefinitionResponse getFieldDefinitionById(Long id) {
        String tenantId = TenantContext.getTenantId();
        FieldDefinition fieldDefinition = fieldDefinitionRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new FieldDefinitionNotFoundException("Field definition with id %d not found".formatted(id)));
        return FieldDefinitionResponse.fromEntity(fieldDefinition);
    }

    /*TODO: getAllFieldDefinitionsForObject has an N+1 query on picklistvalues*/
    public List<FieldDefinitionResponse> getAllFieldDefinitionsForObject(Long objectDefinitionId) {
        String tenantId = TenantContext.getTenantId();
        objectDefinitionRepository.findByIdAndTenantId(objectDefinitionId, tenantId)
                .orElseThrow(() -> new ObjectDefinitionNotFoundException("Object definition with id %d not found".formatted(objectDefinitionId)));
        return fieldDefinitionRepository.findByObjectDefinitionIdAndTenantId(objectDefinitionId, tenantId)
                .stream()
                .map(FieldDefinitionResponse::fromEntity)
                .toList();
    }
}
