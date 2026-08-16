package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, UUID> {

    @Query("""
        SELECT distinct item
        FROM InspectionItem item
        JOIN FETCH item.inspectionLocation location
        JOIN FETCH location.inspection inspection
        JOIN FETCH location.sector sector
        WHERE inspection.site.id = :siteId 
          AND item.situation = NON_CONFORMING
          AND inspection.status != DRAFT
          AND inspection.createdAt = (
              SELECT MAX(i2.createdAt)
              FROM Inspection i2
              JOIN i2.inspectionLocations l2
              WHERE i2.site.id = :siteId 
                AND l2.sector.id = sector.id
                AND i2.status != DRAFT
          )
        ORDER BY item.createdAt DESC
        """)
    List<InspectionItem> findPreviousNonConformitiesBySiteId(@Param("siteId") UUID siteId);

    @Query("""
        SELECT distinct item
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
