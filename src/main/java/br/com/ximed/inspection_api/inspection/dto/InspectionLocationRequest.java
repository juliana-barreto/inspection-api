package br.com.ximed.inspection_api.inspection.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Dados para adicionar uma área ou ambiente inspecionado")
public record InspectionLocationRequest(

        @Schema(description = "ID do setor correspondente")
        @NotNull
        UUID sectorId,

        @Schema(description = "Nome da localização ou área inspecionada", example = "Área administrativa do RH")
        @NotBlank
        @Size(max = 255, message = "Localização deve ter no máximo 255 caracteres")
        String name,

        @Schema(description = "Sublocalização ou ambiente específico", example = "Sala de reuniões")
        @NotBlank
        @Size(max = 255, message = "Sublocalização deve ter no máximo 255 caracteres")
        String sublocation,

        @Schema(description = "Descrição detalhada do ambiente", example = "Local com boa iluminação e ventilação")
        @Size(max = 1000, message = "Descrição do ambiente deve ter no máximo 1000 caracteres")
        String environmentDescription,

        @Schema(description = "Resumo das atividades realizadas no local", example = "Reuniões de planejamento estratégico")
        String activitiesSummary,

        @Schema(description = "Cargos ou funções expostas", example = "Diretor, Gerente, Analista de RH")
        String exposedJobRoles,

        @Schema(description = "Quantidade de trabalhadores expostos no ambiente", example = "3")
        @PositiveOrZero
        Integer exposedWorkersCount,

        @Schema(description = "Ordem da visita da área na inspeção", example = "1")
        @PositiveOrZero
        Integer visitOrder
) {
}
