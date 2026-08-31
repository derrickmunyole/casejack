package com.example.casemanagementplatform.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicklistValueRepository extends JpaRepository<PicklistValue, Long> {
    List<PicklistValue> findByFieldDefinitionIdOrderBySortOrderAsc(Long fieldDefinitionId);

}
