package br.com.ximed.inspection_api.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record InspectionAreaRequest(

        @NotNull
        UUID sectorId,

        @NotBlank
        @Size(max = 255, message = "Localização deve ter no máximo 255 caracteres")
        String locationName,

        @NotBlank
        @Size(max = 255, message = "Sublocalização deve ter no máximo 255 caracteres")
        String sublocationName,

        @Size(max = 1000, message = "Descrição do ambiente deve ter no máximo 1000 caracteres")
        String environmentDescription,

        String activitiesSummary,

        String exposedJobRoles,

        @PositiveOrZero
        Integer exposedWorkersCount,

        @PositiveOrZero
        Integer visitOrder
) {
}
