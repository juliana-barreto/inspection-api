package br.com.ximed.inspection_api.inspection.domain;

import br.com.ximed.inspection_api.company.Sector;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inspection_areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InspectionArea {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "sublocation_name", nullable = false)
    private String sublocationName;

    @Column(name = "environment_description", columnDefinition = "TEXT")
    private String environmentDescription;

    @Column(name = "activities_summary", columnDefinition = "TEXT")
    private String activitiesSummary;

    @Column(name = "exposed_job_roles", columnDefinition = "TEXT")
    private String exposedJobRoles;

    @Column(name = "exposed_workers_count")
    private Integer exposedWorkersCount;

    @Column(name = "visit_order")
    private Integer visitOrder;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @BatchSize(size = 25)
    @Builder.Default
    @OneToMany(mappedBy = "inspectionArea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InspectionItem> items = new ArrayList<>();


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
