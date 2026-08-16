package br.com.ximed.inspection_api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo a identificação básica de um local de inspeção da filial")
public record SiteLocationResponse(
        @Schema(description = "ID único do local de inspeção")
        UUID id,

        @Schema(description = "ID do setor associado")
        UUID sectorId,

        @Schema(description = "Nome do setor associado", example = "Escritório de RH")
        String sectorName,

        @Schema(description = "Nome do local de inspeção", example = "Galpão Principal")
        String name,

        @Schema(description = "Sublocalização ou complemento", example = "Área de Carga e Descarga")
        String sublocation
) {
}
