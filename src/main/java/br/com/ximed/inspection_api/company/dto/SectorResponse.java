package br.com.ximed.inspection_api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo os dados de um setor da filial/estabelecimento")
public record SectorResponse(
        @Schema(description = "ID único do setor")
        UUID id,

        @Schema(description = "Nome do setor", example = "Escritório de RH")
        String name,

        @Schema(description = "Descrição do setor", example = "Setor administrativo")
        String description
) {}
