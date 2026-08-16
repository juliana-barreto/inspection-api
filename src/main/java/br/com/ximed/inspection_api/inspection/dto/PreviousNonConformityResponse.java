package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.RiskLevel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resumo de uma não conformidade identificada em inspeção anterior")
public record PreviousNonConformityResponse(

        @Schema(description = "ID da inspeção anterior")
        UUID inspectionId,

        @Schema(description = "Data e hora em que a inspeção anterior foi iniciada", example = "2026-07-15T10:00:00")
        LocalDateTime inspectionDate,

        @Schema(description = "Nome do setor onde a não conformidade foi identificada", example = "Recursos Humanos")
        String sectorName,

        @Schema(description = "Nome da localização", example = "Área administrativa do RH")
        String name,

        @Schema(description = "Sublocalização ou ambiente específico", example = "Sala de reuniões")
        String sublocationName,

        @Schema(description = "Descrição detalhada da não conformidade anterior", example = "Posto de trabalho com monitor posicionado abaixo da linha de visão e cadeira sem ajuste adequado de apoio lombar.")
        String description,

        @Schema(description = "Nível de risco da não conformidade", example = "MEDIO")
        RiskLevel riskLevel,

        @Schema(description = "Medida corretiva recomendada anteriormente", example = "Adequar o posto de trabalho, ajustando a altura do monitor e disponibilizando cadeira com regulagens compatíveis com o trabalhador.")
        String correctiveMeasure,

        @Schema(description = "Prazo estipulado para a adequação", example = "2026-08-30")
        LocalDate deadline
) {
}
