package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionSituation;
import br.com.ximed.inspection_api.inspection.domain.enums.Probability;
import br.com.ximed.inspection_api.inspection.domain.enums.RegulatoryStandard;
import br.com.ximed.inspection_api.inspection.domain.enums.RiskLevel;
import br.com.ximed.inspection_api.inspection.domain.enums.RiskType;
import br.com.ximed.inspection_api.inspection.domain.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "Dados detalhados de um item inspecionado")
public record InspectionItemResponse(
        @Schema(description = "ID único do item inspecionado")
        UUID id,

        @Schema(description = "Situação da inspeção (CONFORME, NAO_CONFORME, NAO_APLICAVEL)", example = "NAO_CONFORME")
        InspectionSituation situation,

        @Schema(description = "Descrição detalhada do item inspecionado", example = "Posto de trabalho com monitor posicionado abaixo da linha de visão e cadeira sem ajuste adequado de apoio lombar.")
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

        @Schema(description = "Nível de risco calculado pelo backend (BAIXO, MEDIO, ALTO, CRITICO)", example = "MEDIO")
        RiskLevel riskLevel,

        @Schema(description = "Norma regulamentadora aplicável", example = "NR_17")
        RegulatoryStandard regulatoryStandard,

        @Schema(description = "Item específico da NR aplicável", example = "17.5.3")
        String nrItem,

        @Schema(description = "Medida corretiva recomendada", example = "Adequar o posto de trabalho, ajustando a altura do monitor e disponibilizando cadeira com regulagens compatíveis com o trabalhador.")
        String correctiveMeasure,

        @Schema(description = "Nome do responsável pela adequação", example = "Gestor de Recursos Humanos")
        String responsibleName,

        @Schema(description = "Prazo limite para cumprimento", example = "2026-08-30")
        LocalDate deadline,

        @Schema(description = "Lista de evidências fotográficas anexadas")
        List<EvidenceResponse> evidences
) {
}