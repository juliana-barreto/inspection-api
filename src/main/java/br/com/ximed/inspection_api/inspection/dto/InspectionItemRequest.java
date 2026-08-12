package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Dados para adicionar um item inspecionado (conforme/não conforme)")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InspectionItemRequest(

        @Schema(description = "Situação da inspeção (CONFORME, NAO_CONFORME, NAO_APLICAVEL)", example = "NAO_CONFORME")
        @NotNull
        InspectionSituation situation,

        @Schema(description = "Descrição detalhada do item inspecionado", example = "Posto de trabalho com monitor posicionado abaixo da linha de visão e cadeira sem ajuste adequado de apoio lombar.")
        @NotBlank
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String description,

        @Schema(description = "Tipo de risco identificado", example = "ERGONOMIC")
        RiskType riskType,

        @Schema(description = "Descrição do perigo/fator de risco", example = "Postura inadequada durante a utilização prolongada do computador.")
        String hazardDescription,

        @Schema(description = "Possíveis danos à saúde ou lesões", example = "Desconforto musculoesquelético, dores cervicais e lombares.")
        String possibleHarm,

        @Schema(description = "Probabilidade de ocorrência", example = "POSSIBLE")
        Probability probability,

        @Schema(description = "Severidade das consequências", example = "MODERATE")
        Severity severity,

        @Schema(description = "Norma regulamentadora aplicável", example = "NR_17")
        RegulatoryStandard regulatoryStandard,

        @Schema(description = "Item específico da NR aplicável", example = "17.5.3")
        String nrItem,

        @Schema(description = "Medida corretiva ou de prevenção recomendada", example = "Adequar o posto de trabalho, ajustando a altura do monitor e disponibilizando cadeira com regulagens compatíveis com o trabalhador.")
        String correctiveMeasure,

        @Schema(description = "Nome do responsável pela adequação", example = "Gestor de Recursos Humanos")
        String responsibleName,

        @Schema(description = "Prazo limite para cumprimento da medida corretiva", example = "2026-08-30")
        LocalDate deadline
) {
}
