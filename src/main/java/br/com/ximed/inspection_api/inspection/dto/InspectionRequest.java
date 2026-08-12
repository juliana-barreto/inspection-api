package br.com.ximed.inspection_api.inspection.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para criação ou atualização de uma inspeção de segurança")
public record InspectionRequest(

        @Schema(description = "ID do estabelecimento/filial inspecionado")
        @NotNull
        UUID siteId,

        @Schema(description = "Objetivo principal da inspeção", example = "Inspeção periódica de segurança do trabalho")
        @NotBlank
        String objective,

        @Schema(description = "Indica se a inspeção abrange múltiplos setores", example = "true")
        boolean multisectoral,

        @Schema(description = "Nome do inspetor responsável", example = "João da Silva")
        @NotBlank
        String inspectorName,

        @Schema(description = "Cargo do inspetor", example = "Técnico de Segurança do Trabalho")
        @NotBlank
        String inspectorJobTitle,

        @Schema(description = "Registro técnico do inspetor", example = "MTE 123456/SP")
        String inspectorTechnicalRegistration,

        @Schema(description = "Data e hora de início da inspeção", example = "2026-08-12T09:00:00")
        @NotNull
        LocalDateTime startedAt,

        @Schema(description = "Data e hora de término da inspeção", example = "2026-08-12T12:00:00")
        LocalDateTime endedAt
) {
}
