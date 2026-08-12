package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record InspectionResponse(
        UUID id,
        String objective,
        boolean multisectoral,

        String inspectorName,
        String inspectorJobTitle,
        String inspectorTechnicalRegistration,

        LocalDateTime startedAt,
        LocalDateTime endedAt,
        InspectionStatus status,

        UUID siteId,
        String siteName,

        List<InspectionAreaResponse> areas,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}