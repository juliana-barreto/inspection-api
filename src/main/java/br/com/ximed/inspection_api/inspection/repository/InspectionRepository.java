package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface InspectionRepository extends JpaRepository<Inspection, UUID> {
        @Query("""
            SELECT DISTINCT i
            FROM Inspection i
            LEFT JOIN FETCH i.site
            LEFT JOIN FETCH i.inspectionLocations location
            LEFT JOIN FETCH location.sector
            LEFT JOIN FETCH location.items item
            LEFT JOIN FETCH item.evidences
            WHERE i.id = :id
            """)
    Optional<Inspection> findByIdWithReportData(@Param("id") UUID id);
}
