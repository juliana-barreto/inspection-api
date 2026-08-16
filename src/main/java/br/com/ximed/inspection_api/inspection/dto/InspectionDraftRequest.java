package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para atualização parcial (rascunho/progresso) de uma inspeção de segurança")
public record InspectionDraftRequest(

        @Schema(description = "ID do estabelecimento/filial inspecionado")
        UUID siteId,

        @Schema(description = "Objetivo principal da inspeção", example = "Inspeção periódica de segurança do trabalho")
        String objective,

        @Schema(description = "Indica se a inspeção abrange múltiplos setores", example = "true")
        Boolean multisectoral,

        @Schema(description = "Nome do inspetor responsável", example = "João da Silva")
        String inspectorName,

        @Schema(description = "Cargo do inspetor", example = "Técnico de Segurança do Trabalho")
        String inspectorJobTitle,

        @Schema(description = "Registro técnico do inspetor", example = "MTE 123456/SP")
        String inspectorTechnicalRegistration,

        @Schema(description = "Data e hora de início da inspeção", example = "2026-08-12T09:00:00")
        @PastOrPresent(message = "A data e hora de início não podem estar no futuro")
        LocalDateTime startedAt,

        @Schema(description = "Data e hora de término da inspeção", example = "2026-08-12T12:00:00")
        LocalDateTime endedAt,

        @Schema(description = "Status opcional para atualização direta", example = "DRAFT")
        InspectionStatus status
) {
}
