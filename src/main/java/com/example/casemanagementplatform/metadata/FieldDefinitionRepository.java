package com.example.casemanagementplatform.metadata;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldDefinitionRepository extends JpaRepository<FieldDefinition, Long> {
    Optional<FieldDefinition> findByIdAndTenantId(Long id, String tenantId);

    List<FieldDefinition> findAllByTenantId(String tenantId);

    Optional<FieldDefinition> findByObjectDefinitionIdAndTenantId(Long id, String tenantId);

    Optional<FieldDefinition> findByObjectDefinitionIdAndNameAndTenantId(Long objectDefinitionId, String name, String tenantId);

    boolean existsByObjectDefinitionIdAndNameAndTenantId(Long objectDefinitionId, String Name, String tenantId);

}
