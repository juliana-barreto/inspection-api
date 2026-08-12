package br.com.ximed.inspection_api.inspection.domain;

import br.com.ximed.inspection_api.inspection.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inspection_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InspectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_area_id", nullable = false)
    private InspectionArea inspectionArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private InspectionSituation situation;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_type")
    private RiskType riskType;

    @Column(name = "hazard_description", columnDefinition = "TEXT")
    private String hazardDescription;

    @Column(name = "possible_harm", columnDefinition = "TEXT")
    private String possibleHarm;

    @Enumerated(EnumType.STRING)
    @Column(name = "probability")
    private Probability probability;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "regulatory_standard")
    private RegulatoryStandard regulatoryStandard;

    @Column(name = "nr_item")
    private String nrItem;

    @Column(name = "corrective_measure", columnDefinition = "TEXT")
    private String correctiveMeasure;

    @Column(name = "responsible_name")
    private String responsibleName;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @BatchSize(size = 25)
    @Builder.Default
    @OneToMany(mappedBy = "inspectionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evidence> evidences = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
