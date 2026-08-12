package br.com.ximed.inspection_api.inspection.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Dados de uma área ou ambiente inspecionado")
public record InspectionLocationResponse(
        @Schema(description = "ID único da área inspecionada")
        UUID id,

        @Schema(description = "ID do setor vinculado")
        UUID sectorId,

        @Schema(description = "Nome do setor vinculado", example = "Recursos Humanos")
        String sector,

        @Schema(description = "Nome da localização inspecionada", example = "Sala administrativa do RH")
        String name,

        @Schema(description = "Sublocalização ou ambiente específico", example = "Sala de reuniões")
        String sublocation,

        @Schema(description = "Descrição do ambiente de trabalho", example = "Ambiente bem iluminado com ventilação natural")
        String environmentDescription,

        @Schema(description = "Resumo das atividades desenvolvidas", example = "Reuniões de planejamento estratégico")
        String activitiesSummary,

        @Schema(description = "Cargos ou funções expostas", example = "Diretor, Gerente, Analista de RH")
        String exposedJobRoles,

        @Schema(description = "Quantidade de trabalhadores expostos", example = "3")
        Integer exposedWorkersCount,

        @Schema(description = "Ordem da visita da área na inspeção", example = "1")
        Integer visitOrder,

        @Schema(description = "Lista de itens inspecionados nesta área")
        List<InspectionFindingResponse> items
) {
}