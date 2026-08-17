package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, UUID> {

    @Query("""
        SELECT item
        FROM InspectionItem item
        JOIN FETCH item.inspectionLocation location
        JOIN FETCH location.inspection inspection
        WHERE item.id = :id
          AND location.id = :locationId
          AND inspection.id = :inspectionId
        """)
    Optional<InspectionItem> findByIdAndLocationIdAndInspectionId(
            @Param("id") UUID id,
            @Param("locationId") UUID locationId,
            @Param("inspectionId") UUID inspectionId
    );
}
