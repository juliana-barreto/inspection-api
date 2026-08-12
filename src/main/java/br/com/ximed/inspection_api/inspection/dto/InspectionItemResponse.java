package br.com.ximed.inspection_api.inspection.dto;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionSituation;
import br.com.ximed.inspection_api.inspection.domain.enums.Probability;
import br.com.ximed.inspection_api.inspection.domain.enums.RegulatoryStandard;
import br.com.ximed.inspection_api.inspection.domain.enums.RiskLevel;
import br.com.ximed.inspection_api.inspection.domain.enums.RiskType;
import br.com.ximed.inspection_api.inspection.domain.enums.Severity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record InspectionItemResponse(
        UUID id,

        InspectionSituation situation,
        String description,

        RiskType riskType,
        String hazardDescription,
        String possibleHarm,

        Probability probability,
        Severity severity,

        // Nível de risco calculado pelo backend
        RiskLevel riskLevel,

        RegulatoryStandard regulatoryStandard,
        String nrItem,

        String correctiveMeasure,
        String responsibleName,
        LocalDate deadline,

        List<EvidenceResponse> evidences
) {
}