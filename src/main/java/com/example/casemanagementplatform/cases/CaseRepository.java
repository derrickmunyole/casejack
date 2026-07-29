package com.example.casemanagementplatform.cases;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRepository  extends JpaRepository<Case, Long> {
    List<Case> findByStatus(Case.CaseStatus status);
}