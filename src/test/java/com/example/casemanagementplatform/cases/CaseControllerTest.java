package com.example.casemanagementplatform.cases;

import com.example.casemanagementplatform.common.exceptions.CaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CaseService caseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCase_returns201() throws Exception {
        CaseRequest caseRequest = new CaseRequest();
        caseRequest.setSubject("Test subject");
        caseRequest.setDescription("Test Description");
        caseRequest.setPriority(Case.CasePriority.MEDIUM);
        caseRequest.setStatus(Case.CaseStatus.OPEN);


        CaseResponse response = new CaseResponse(
                1L,
                "Test subject",
                "Test description",
                Case.CaseStatus.OPEN,
                Case.CasePriority.MEDIUM,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(caseService.createCase(any(CaseRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/cases")
                        .header("X-Tenant-ID", "x-tenant-a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(caseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Test subject"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    @Test
    void getAllCases_returnsListOfCases() throws Exception {
        CaseResponse caseResponse = new CaseResponse(
                1L,
                "Test subject",
                "Test description",
                Case.CaseStatus.CLOSED,
                Case.CasePriority.HIGH,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        CaseResponse caseResponse2 = new CaseResponse(
                2L,
                "A test case",
                "Second test case",
                Case.CaseStatus.IN_PROGRESS,
                Case.CasePriority.LOW,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(caseService.getAllCases()).thenReturn(List.of(caseResponse, caseResponse2));

        mockMvc.perform(get("/api/cases")
                        .header("X-Tenant-ID", "x-tenant-a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].subject").value("Test subject"))
                .andExpect(jsonPath("$[1].subject").value("A test case"));
    }

    @Test
    void getCase_returnsCaseById() throws Exception {
        CaseResponse caseResponse = new CaseResponse(
                1L,
                "Test subject",
                "Test description",
                Case.CaseStatus.OPEN,
                Case.CasePriority.LOW,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(caseService.getCaseById(1L)).thenReturn(caseResponse);

        mockMvc.perform(get("/api/cases/1")
                        .header("X-Tenant-ID", "x-tenant-a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    @Test
    void getCaseById_returns404WhenCaseNotFound() throws Exception {
        when(caseService.getCaseById(25L)).thenThrow(new CaseNotFoundException(
                "Case with id 25 not found"
        ));

        mockMvc.perform(get("/api/cases/25")
                        .header("X-Tenant-ID", "x-tenant-a"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Case with id 25 not found"))
                .andExpect(jsonPath("$.path").value("/api/cases/25"));

    }

    @Test
    void updateCase_returns200WhenUpdated() throws Exception {
        CaseRequest caseRequest = new CaseRequest();
        caseRequest.setSubject("Test subject");
        caseRequest.setDescription("Test Description");
        caseRequest.setPriority(Case.CasePriority.MEDIUM);
        caseRequest.setStatus(Case.CaseStatus.OPEN);

        CaseResponse response = new CaseResponse(
                1L,
                "Test subject",
                "Test description",
                Case.CaseStatus.IN_PROGRESS,
                Case.CasePriority.MEDIUM,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(caseService.updateCase(eq(1L), any(CaseRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/cases/1")
                        .header("X-Tenant-ID", "x-tenant-a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(caseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void updateCase_returns404WhenCaseNotFound() throws Exception {
        CaseRequest caseRequest = new CaseRequest();
        caseRequest.setSubject("Test subject");
        caseRequest.setStatus(Case.CaseStatus.OPEN);
        caseRequest.setPriority(Case.CasePriority.MEDIUM);

        when(caseService.updateCase(eq(63L), any(CaseRequest.class))).thenThrow(new CaseNotFoundException(
                "Case with id 63 not found"
        ));

        mockMvc.perform(put("/api/cases/63")
                        .header("X-Tenant-ID", "x-tenant-a")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(caseRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Case with id 63 not found"))
                .andExpect(jsonPath("$.path").value("/api/cases/63"));
    }

    @Test
    void deleteCase_returns204WhenDeleted() throws Exception {
        mockMvc.perform(delete("/api/cases/1")
                .header("X-Tenant-ID", "x-tenant-a"))
                .andExpect(status().isNoContent());

        verify(caseService, times(1)).deleteCase(eq(1L));
    }

    @Test
    void deleteCase_returns404WhenCaseNotFound() throws Exception {
        doThrow(new CaseNotFoundException("Case with id 1 not found"))
                .when(caseService).deleteCase(eq(1L));

        mockMvc.perform(delete("/api/cases/1")
                        .header("X-Tenant-ID", "x-tenant-a"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Case with id 1 not found"));


    }

    @Test
    void request_returns400WhenTenantHeaderMissing() throws Exception {
        mockMvc.perform(get("/api/cases"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing required header 'x-tenant-id'"));
    }
}
