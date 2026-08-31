package com.example.casemanagementplatform.metadata;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/field-definitions")
public class FieldDefinitionController {
    public final FieldDefinitionService fieldDefinitionService;

    public FieldDefinitionController(FieldDefinitionService fieldDefinitionService) {
        this.fieldDefinitionService = fieldDefinitionService;
    }

    @PostMapping
    public ResponseEntity<FieldDefinitionResponse> createFieldDefinition(@Valid @RequestBody FieldDefinitionRequest request) {
        FieldDefinitionResponse response = fieldDefinitionService.createFieldDefinition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDefinitionResponse> getFieldDefinitionById(@PathVariable Long id) {
        FieldDefinitionResponse fieldDefinitionById = fieldDefinitionService.getFieldDefinitionById(id);
        return ResponseEntity.ok(fieldDefinitionById);
    }

    @GetMapping("/{objectDefinitionId}/fields")
    public ResponseEntity<List<FieldDefinitionResponse>> getAllFieldDefinitionsForObject(@PathVariable Long objectDefinitionId) {
        List<FieldDefinitionResponse> allFieldDefinitionsForObject = fieldDefinitionService.getAllFieldDefinitionsForObject(objectDefinitionId);
        return ResponseEntity.ok(allFieldDefinitionsForObject);
    }
}
