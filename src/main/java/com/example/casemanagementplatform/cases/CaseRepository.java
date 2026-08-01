package com.example.casemanagementplatform.cases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaseRepository  extends JpaRepository<Case, Long> {
    List<Case> findByStatus(Case.CaseStatus status);
    Optional<Case> findByIdAndTenantId(long id, String tenantId);
    List<Case> findAllByTenantId(String tenantId);
}