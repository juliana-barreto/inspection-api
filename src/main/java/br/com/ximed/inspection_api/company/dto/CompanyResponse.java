package br.com.ximed.inspection_api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta contendo os dados cadastrais da empresa")
public record CompanyResponse(
        @Schema(description = "ID único da empresa")
        UUID id,

        @Schema(description = "Razão social da empresa", example = "Ammarhes Consultoria em Segurança e Medicina do Trabalho Ltda. - ME")
        String corporateName,

        @Schema(description = "Nome fantasia da empresa", example = "Ximed Saúde Ocupacional")
        String tradeName
) {}