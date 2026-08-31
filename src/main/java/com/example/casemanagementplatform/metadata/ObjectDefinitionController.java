package com.example.casemanagementplatform.metadata;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/object-definitions")
public class ObjectDefinitionController {

    private final ObjectDefinitionService objectDefinitionService;

    public ObjectDefinitionController(ObjectDefinitionService objectDefinitionService) {
        this.objectDefinitionService = objectDefinitionService;
    }

    @PostMapping
    public ResponseEntity<ObjectDefinitionResponse> createObjectDefinition(@Valid @RequestBody ObjectDefinitionRequest request) {
        ObjectDefinitionResponse response =  objectDefinitionService.createObjectDefinition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ObjectDefinitionResponse>> getAllObjectDefinitions() {
        List<ObjectDefinitionResponse> allObjectDefinitions = objectDefinitionService.getAllObjectDefinitions();
        return ResponseEntity.ok(allObjectDefinitions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectDefinitionResponse> getObjectDefinitionById(@PathVariable Long id) {
        ObjectDefinitionResponse objectDefinitionById = objectDefinitionService.getObjectDefinitionById(id);
        return ResponseEntity.ok(objectDefinitionById);
    }


}
