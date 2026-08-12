package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InspectionItemRequest(

        @NotNull
        InspectionSituation situation,

        @NotBlank
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String description,

        RiskType riskType,

        String hazardDescription,

        String possibleHarm,

        Probability probability,

        Severity severity,

        RegulatoryStandard regulatoryStandard,

        String nrItem,

        String correctiveMeasure,

        String responsibleName,

        LocalDate deadline
) {
}
