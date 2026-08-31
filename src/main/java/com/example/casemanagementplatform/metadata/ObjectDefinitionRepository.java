package com.example.casemanagementplatform.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObjectDefinitionRepository extends JpaRepository<ObjectDefinition, Long> {
    Optional<ObjectDefinition> findByTenantIdAndName(String tenantId, String name);

    List<ObjectDefinition> findByTenantId(String tenantId);

    Optional<ObjectDefinition> findByIdAndTenantId(Long id, String tenantId);

    boolean existsByTenantIdAndName(String tenantId, String name);
}
