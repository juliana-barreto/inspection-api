package br.com.ximed.inspection_api.inspection.dto;

import java.util.List;
import java.util.UUID;

public record InspectionAreaResponse(
        UUID id,

        UUID sectorId,
        String sectorName,

        String locationName,
        String sublocationName,

        String environmentDescription,
        String activitiesSummary,

        String exposedJobRoles,
        Integer exposedWorkersCount,

        Integer visitOrder,

        List<InspectionItemResponse> items
) {
}