package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.InspectionLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface InspectionLocationRepository extends JpaRepository<InspectionLocation, UUID> {

    @Query("""
            SELECT DISTINCT location
            FROM InspectionLocation location
            JOIN FETCH location.inspection inspection
            LEFT JOIN FETCH location.sector sector
            WHERE location.id = :id AND inspection.id = :inspectionId
            """)
    Optional<InspectionLocation> findByIdAndInspectionId(UUID id, UUID inspectionId);
}
