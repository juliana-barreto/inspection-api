package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Resumo do relatório de inspeção para listagem")
public record InspectionSummaryResponse(
        @Schema(description = "ID único da inspeção")
        UUID id,

        @Schema(description = "Código de identificação do relatório", example = "INS-2026-001")
        String code,

        @Schema(description = "Status atual da inspeção", example = "EM_ANDAMENTO")
        InspectionStatus status,

        @Schema(description = "Data e hora de término da inspeção", example = "2026-08-12T12:00:00")
        LocalDateTime endedAt,

        @Schema(description = "Nome do inspetor responsável", example = "João da Silva")
        String inspectorName,

        @Schema(description = "ID da filial (site)")
        UUID siteId,

        @Schema(description = "Nome da empresa")
        String companyName,

        @Schema(description = "Nome da filial")
        String siteName,

        @Schema(description = "Nomes dos locais inspecionados")
        List<String> locationNames
) {
}
