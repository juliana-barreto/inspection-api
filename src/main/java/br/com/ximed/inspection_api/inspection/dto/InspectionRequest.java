package br.com.ximed.inspection_api.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record InspectionRequest(

        // Obrigatório na criação. Na atualização, se informado, o site pode ser validado/mantido.
        @NotNull
        UUID siteId,

        @NotBlank
        String objective,

        boolean multisectoral,

        @NotBlank
        String inspectorName,

        @NotBlank
        String inspectorJobTitle,

        String inspectorTechnicalRegistration,

        @NotNull
        LocalDateTime startedAt,

        LocalDateTime endedAt
) {
}
