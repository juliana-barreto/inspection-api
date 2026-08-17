package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.Inspection;
import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InspectionRepository extends JpaRepository<Inspection, UUID> {

    Optional<Inspection> findFirstByStatusOrderByCreatedAtDesc(InspectionStatus status);


    @Query("""
            SELECT i
            FROM Inspection i
            LEFT JOIN FETCH i.site
            WHERE (:siteId IS NULL OR i.site.id = :siteId)
              AND (:status IS NULL OR i.status = :status)
            ORDER BY i.createdAt DESC
            """)
    List<Inspection> findAllBySiteAndStatus(
            @Param("siteId") UUID siteId,
            @Param("status") InspectionStatus status
    );
}
