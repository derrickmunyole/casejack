package com.example.casemanagementplatform.metadata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "picklist_values")
@Getter
@NoArgsConstructor
public class PicklistValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_definition_id", nullable = false)
    private FieldDefinition fieldDefinition;

    @Setter
    @Column(nullable = false)
    private String value;

    @Setter
    private Integer sortOrder;

    public PicklistValue(FieldDefinition fieldDefinition, String value, Integer sortOrder) {
        this.fieldDefinition = fieldDefinition;
        this.value = value;
        this.sortOrder = sortOrder;
    }
}
