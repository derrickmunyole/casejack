package com.example.casemanagementplatform.cases;

import com.example.casemanagementplatform.common.exceptions.CaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaseServiceTest {
    @Mock
    private CaseRepository caseRepository;
    @InjectMocks
    private CaseService caseService;

    @Test
    void createCase_SavesAndReturnsCase(){
        CaseRequest caseRequest = new CaseRequest();
        caseRequest.setSubject("Test subject");
        caseRequest.setDescription("Test Description");
        caseRequest.setPriority(Case.CasePriority.HIGH);
        caseRequest.setStatus(Case.CaseStatus.OPEN);

        Case savedCase = new Case(
                "Test subject",
                "Test Description",
                Case.CasePriority.HIGH,
                Case.CaseStatus.OPEN
        );

        ReflectionTestUtils.setField(savedCase, "id", 1L);
        ReflectionTestUtils.setField(savedCase, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(savedCase, "updatedAt", LocalDateTime.now());

        when(caseRepository.save(any(Case.class))).thenReturn(savedCase);

        CaseResponse savedCaseResponse = caseService.createCase(caseRequest);

        assertThat(savedCaseResponse.getId()).isEqualTo(1L);
        assertThat(savedCaseResponse.getSubject()).isEqualTo("Test subject");
        assertThat(savedCaseResponse.getDescription()).isEqualTo("Test Description");
        assertThat(savedCaseResponse.getPriority()).isEqualTo(Case.CasePriority.HIGH);
        assertThat(savedCaseResponse.getStatus()).isEqualTo(Case.CaseStatus.OPEN);

        verify(caseRepository, times(1)).save(any(Case.class));
    }

    @Test
    void getCaseById_returnsCaseWhenFound(){
        Case existingCase = new Case(
                "Test subject",
                "Test description",
                Case.CasePriority.HIGH,
                Case.CaseStatus.OPEN
        );

        ReflectionTestUtils.setField(existingCase, "id", 1L);
        ReflectionTestUtils.setField(existingCase, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(existingCase, "updatedAt", LocalDateTime.now());

        when(caseRepository.findById(1L)).thenReturn(Optional.of(existingCase));

        CaseResponse result = caseService.getCaseById(1L);

        assertThat(result.getId()).isEqualTo(1L);

        assertThat(result.getSubject()).isEqualTo("Test subject");
        assertThat(result.getDescription()).isEqualTo("Test description");
    }

    @Test
    void getCaseById_throwsWhenNotFound(){
        when(caseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CaseNotFoundException.class, () -> caseService.getCaseById(1L));
    }

    @Test
    void updateCaseById_updatesCase(){
        Case existingCase = new Case(
                "Test subject",
                "Test description",
                Case.CasePriority.HIGH,
                Case.CaseStatus.OPEN
        );

        ReflectionTestUtils.setField(existingCase, "id", 1L);
        ReflectionTestUtils.setField(existingCase, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(existingCase, "updatedAt", LocalDateTime.now());

        when(caseRepository.findById(1L)).thenReturn(Optional.of(existingCase));
        when(caseRepository.save(any(Case.class))).thenReturn(existingCase);

        CaseRequest caseRequest = new CaseRequest();
        caseRequest.setSubject("Updated test subject");
        caseRequest.setDescription("Test description");
        caseRequest.setPriority(Case.CasePriority.HIGH);
        caseRequest.setStatus(Case.CaseStatus.OPEN);

        CaseResponse response = caseService.updateCase(1L, caseRequest);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getSubject()).isEqualTo("Updated test subject");

    }

    @Test
    void updateCaseById_throwsWhenNotFound(){
        when(caseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CaseNotFoundException.class, () -> caseService.updateCase(1L, new CaseRequest()));
    }

    @Test
    void deleteCaseById_deletesCase() {
        caseService.deleteCase(1L);

        verify(caseRepository, times(1)).deleteById(1L);
    }
}
