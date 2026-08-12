package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Dados detalhados de uma inspeção de segurança")
public record InspectionResponse(
        @Schema(description = "ID único da inspeção")
        UUID id,

        @Schema(description = "Objetivo principal da inspeção", example = "Inspeção periódica de segurança do trabalho")
        String objective,

        @Schema(description = "Indica se a inspeção abrange múltiplos setores", example = "true")
        boolean multisectoral,

        @Schema(description = "Nome do inspetor responsável", example = "João da Silva")
        String inspectorName,

        @Schema(description = "Cargo do inspetor", example = "Técnico de Segurança do Trabalho")
        String inspectorJobTitle,

        @Schema(description = "Registro técnico do inspetor", example = "MTE 123456/SP")
        String inspectorTechnicalRegistration,

        @Schema(description = "Data e hora de início da inspeção", example = "2026-08-12T09:00:00")
        LocalDateTime startedAt,

        @Schema(description = "Data e hora de término da inspeção", example = "2026-08-12T12:00:00")
        LocalDateTime endedAt,

        @Schema(description = "Status atual da inspeção", example = "EM_ANDAMENTO")
        InspectionStatus status,

        @Schema(description = "ID do estabelecimento/filial inspecionado")
        UUID siteId,

        @Schema(description = "Nome do estabelecimento/filial inspecionado", example = "Sede/Del Castilho")
        String siteName,

        @Schema(description = "Lista de áreas inspecionadas")
        List<InspectionAreaResponse> areas,

        @Schema(description = "Data de criação do registro", example = "2026-08-12T08:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Data da última atualização do registro", example = "2026-08-12T08:30:00")
        LocalDateTime updatedAt
) {
}