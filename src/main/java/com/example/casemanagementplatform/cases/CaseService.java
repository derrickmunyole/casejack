package com.example.casemanagementplatform.cases;

import com.example.casemanagementplatform.common.exceptions.CaseNotFoundException;
import com.example.casemanagementplatform.common.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    /**
     * Creates a new Case. Accepts a tenant id parameter to create a
     * case, allowing to filter cases based on the tenant id
     *
     * @param request
     * @return the created case, including generated id, timestamps, and tenant id
     */
    public CaseResponse createCase(CaseRequest request){
        Case newCase = new Case(
                request.getSubject(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                TenantContext.getTenantId()
        );

        Case savedCase = caseRepository.save(newCase);
        return CaseResponse.fromEntity(savedCase);
    }

    public CaseResponse getCaseById(Long id) {
        Case resultCase = caseRepository.findById(id).orElseThrow(()-> new CaseNotFoundException("Case with id %d not found".formatted(id)));
        return CaseResponse.fromEntity(resultCase);
    }

    public List<CaseResponse> getAllCases(){
        return caseRepository.findAll().stream().map(CaseResponse::fromEntity).toList();
    }

    public CaseResponse updateCase(Long id, CaseRequest request){
        Case existingCase = caseRepository.findById(id).orElseThrow(()-> new CaseNotFoundException("Case with id %d not found".formatted(id)));
        existingCase.setSubject(request.getSubject());
        existingCase.setDescription(request.getDescription());
        existingCase.setPriority(request.getPriority());
        existingCase.setStatus(request.getStatus());
        Case updatedCase = caseRepository.save(existingCase);
        return CaseResponse.fromEntity(updatedCase);
    }

    public void deleteCase(Long id){
        if(!caseRepository.existsById(id)){
            throw new CaseNotFoundException("Case with id %d not found".formatted(id));
        }
        caseRepository.deleteById(id);
    }


}
