package br.com.ximed.inspection_api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo a identificação do local")
public record SiteResponse(
        @Schema(description = "ID único do estabelecimento")
        UUID id,

        @Schema(description = "ID da empresa")
        UUID companyId,

        @Schema(description = "Nome do estabelecimento/filial", example = "Sede/Del Castilho")
        String name,

        @Schema(description = "CNPJ do estabelecimento", example = "12.345.678/0001-90")
        String cnpj,

        @Schema(description = "Código CNAE principal", example = "8630-5/03")
        String cnae,

        @Schema(description = "Endereço completo do estabelecimento", example = "Avenida Pastor Martin Luther King Jr., 126, Del Castilho - Rio de Janeiro/RJ - CEP 20765-000")
        String address
) {}
