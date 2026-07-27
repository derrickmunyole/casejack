package com.example.casemanagementplatform.cases;

import com.example.casemanagementplatform.common.exceptions.CaseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public CaseResponse createCase(CaseRequest request){
        Case newCase = new Case(
                request.getSubject(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus()
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
        caseRepository.deleteById(id);
    }


}
