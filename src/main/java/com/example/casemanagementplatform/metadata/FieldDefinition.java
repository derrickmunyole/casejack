package com.example.casemanagementplatform.metadata;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "field_definitions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"object_definition_id", "name"}))
@Getter
@NoArgsConstructor
public class FieldDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @ManyToOne
    @JoinColumn(name = "object_definition_id", nullable = false)
    private ObjectDefinition objectDefinition;

    @Setter
    @Column(nullable = false)
    private String name;

    public enum FieldType {
        TEXT,
        NUMBER,
        BOOLEAN,
        DATE,
        PICKLIST
    }

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;

    @Setter
    @Column(nullable = false)
    private boolean required;

    @Setter
    private String defaultValue;

    @OneToMany(mappedBy = "fieldDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PicklistValue> picklistValues = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public FieldDefinition(String name, FieldType fieldType,  ObjectDefinition objectDefinition, boolean required, String defaultValue, String tenantId) {
        this.name = name;
        this.fieldType = fieldType;
        this.objectDefinition = objectDefinition;
        this.required = required;
        this.defaultValue = defaultValue;
        this.tenantId = tenantId;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
