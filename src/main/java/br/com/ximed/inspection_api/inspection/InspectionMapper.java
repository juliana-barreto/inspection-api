package br.com.ximed.inspection_api.inspection;

import br.com.ximed.inspection_api.company.Sector;
import br.com.ximed.inspection_api.company.Site;
import br.com.ximed.inspection_api.inspection.domain.Evidence;
import br.com.ximed.inspection_api.inspection.domain.Inspection;
import br.com.ximed.inspection_api.inspection.domain.InspectionItem;
import br.com.ximed.inspection_api.inspection.domain.InspectionLocation;
import br.com.ximed.inspection_api.inspection.domain.enums.RiskLevel;
import br.com.ximed.inspection_api.company.dto.SiteLocationResponse;
import br.com.ximed.inspection_api.company.dto.SectorResponse;
import br.com.ximed.inspection_api.inspection.dto.*;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Component
public class InspectionMapper {

    public InspectionResponse toResponse(Inspection inspection) {
        if (inspection == null) {
            return null;
        }

        UUID siteId = inspection.getSite() != null ? inspection.getSite().getId() : null;
        String siteName = inspection.getSite() != null ? inspection.getSite().getName() : null;

        List<InspectionLocationResponse> locationResponses = inspection.getInspectionLocations() != null
                ? inspection.getInspectionLocations().stream().map(this::toLocationResponse).toList()
                : Collections.emptyList();

        return new InspectionResponse(
                inspection.getId(),
                inspection.getCode(),
                inspection.getObjective(),
                inspection.isMultisectoral(),

                inspection.getInspectorName(),
                inspection.getInspectorJobTitle(),
                inspection.getInspectorTechnicalRegistration(),

                inspection.getStartedAt(),
                inspection.getEndedAt(),
                inspection.getStatus(),

                siteId,
                siteName,

                locationResponses,

                inspection.getCreatedAt(),
                inspection.getUpdatedAt()
        );
    }

    public InspectionSummaryResponse toSummaryResponse(Inspection inspection) {
        if (inspection == null) {
            return null;
        }

        List<String> locationNames = inspection.getInspectionLocations() != null
                ? inspection.getInspectionLocations().stream().map(InspectionLocation::getName).toList()
                : Collections.emptyList();

        UUID siteId = inspection.getSite() != null ? inspection.getSite().getId() : null;
        String siteName = inspection.getSite() != null ? inspection.getSite().getName() : null;
        String companyName = inspection.getSite() != null && inspection.getSite().getCompany() != null ? inspection.getSite().getCompany().getTradeName() : null;

        return new InspectionSummaryResponse(
                inspection.getId(),
                inspection.getCode(),
                inspection.getStatus(),
                inspection.getEndedAt(),
                inspection.getInspectorName(),
                siteId,
                companyName,
                siteName,
                locationNames
        );
    }

    public InspectionLocationResponse toLocationResponse(InspectionLocation location) {

        if (location == null) {
            return null;
        }

        UUID sectorId = location.getSector() != null ? location.getSector().getId() : null;
        String sectorName = location.getSector() != null ? location.getSector().getName() : null;


        List<InspectionItemResponse> itemResponses = location.getItems() != null
                ? location.getItems().stream().map(this::toItemResponse).toList()
                : Collections.emptyList();

        return new InspectionLocationResponse(
                location.getId(),

                sectorId,
                sectorName,

                location.getName(),
                location.getSublocation(),

                location.getEnvironmentDescription(),
                location.getActivitiesSummary(),

                location.getExposedJobRoles(),
                location.getExposedWorkersCount(),

                location.getVisitOrder(),

                itemResponses
        );
    }

    public SiteLocationResponse toSiteLocationResponse(InspectionLocation location) {
        if (location == null) {
            return null;
        }

        UUID sectorId = location.getSector() != null ? location.getSector().getId() : null;
        String sectorName = location.getSector() != null ? location.getSector().getName() : null;

        return new SiteLocationResponse(
                location.getId(),
                sectorId,
                sectorName,
                location.getName(),
                location.getSublocation()
        );
    }


    public InspectionItemResponse toItemResponse(InspectionItem item) {
        if (item == null) {
            return null;
        }

        List<EvidenceResponse> evidenceResponses = item.getEvidences() != null
                ? item.getEvidences().stream().map(this::toEvidenceResponse).toList()
                : Collections.emptyList();

        return new InspectionItemResponse(
                item.getId(),
                item.getSituation(),
                item.getDescription(),

                item.getRiskType(),
                item.getHazardDescription(),
                item.getPossibleHarm(),

                item.getProbability(),
                item.getSeverity(),
                item.getRiskLevel(),

                item.getRegulatoryStandard(),
                item.getNrItem(),

                item.getCorrectiveMeasure(),
                item.getResponsibleName(),
                item.getDeadline(),

                evidenceResponses
        );
    }

    public EvidenceResponse toEvidenceResponse(Evidence evidence) {
        if (evidence == null) {
            return null;
        }

        return new EvidenceResponse(
                evidence.getId(),
                evidence.getImgUrl(),
                evidence.getCaption()
        );
    }

    public PreviousNonConformityResponse toPreviousNonConformityResponse(InspectionItem item) {
        if (item == null) {
            return null;
        }

        InspectionLocation location = item.getInspectionLocation();
        Inspection inspection = location != null ? location.getInspection() : null;
        String sectorName = location != null && location.getSector() != null ? location.getSector().getName() : null;

        return new PreviousNonConformityResponse(
                inspection != null ? inspection.getId() : null,
                inspection != null ? inspection.getStartedAt() : null,

                sectorName,
                location != null ? location.getName() : null,
                location != null ? location.getSublocation() : null,

                item.getDescription(),
                item.getRiskLevel(),

                item.getCorrectiveMeasure(),
                item.getDeadline()
        );
    }

    public Inspection toEntity(InspectionRequest request, Site site) {
        if (request == null) {
            return null;
        }

        return Inspection.builder()
                .site(site)
                .objective(request.objective())
                .multisectoral(request.multisectoral())
                .inspectorName(request.inspectorName())
                .inspectorJobTitle(request.inspectorJobTitle())
                .inspectorTechnicalRegistration(request.inspectorTechnicalRegistration())
                .startedAt(request.startedAt())
                .endedAt(request.endedAt())
                .build();
    }

    public void updateEntityFromRequest(Inspection inspection, InspectionRequest request) {
        if (inspection == null || request == null) {
            return;
        }

        inspection.setObjective(request.objective());
        inspection.setMultisectoral(request.multisectoral());
        inspection.setInspectorName(request.inspectorName());
        inspection.setInspectorJobTitle(request.inspectorJobTitle());
        inspection.setInspectorTechnicalRegistration(request.inspectorTechnicalRegistration());
    }

    public void updateEntityFromDraftRequest(Inspection inspection, InspectionDraftRequest request, Site site) {
        if (inspection == null || request == null) {
            return;
        }

        if (site != null) {
            inspection.setSite(site);
        }
        if (request.objective() != null) {
            inspection.setObjective(request.objective());
        }
        if (request.multisectoral() != null) {
            inspection.setMultisectoral(request.multisectoral());
        }
        if (request.inspectorName() != null) {
            inspection.setInspectorName(request.inspectorName());
        }
        if (request.inspectorJobTitle() != null) {
            inspection.setInspectorJobTitle(request.inspectorJobTitle());
        }
        if (request.inspectorTechnicalRegistration() != null) {
            inspection.setInspectorTechnicalRegistration(request.inspectorTechnicalRegistration());
        }
        if (request.startedAt() != null) {
            inspection.setStartedAt(request.startedAt());
        }
        if (request.endedAt() != null) {
            inspection.setEndedAt(request.endedAt());
        }
        if (request.status() != null) {
            inspection.setStatus(request.status());
        }
    }


    public InspectionLocation toLocationEntity(InspectionLocationRequest request, Inspection inspection, Sector sector) {
        if (request == null) {
            return null;
        }

        return InspectionLocation.builder()
                .inspection(inspection)
                .sector(sector)
                .name(request.name())
                .sublocation(request.sublocation())
                .environmentDescription(request.environmentDescription())
                .activitiesSummary(request.activitiesSummary())
                .exposedJobRoles(request.exposedJobRoles())
                .exposedWorkersCount(request.exposedWorkersCount())
                .visitOrder(request.visitOrder())
                .build();
    }

    public InspectionItem toItemEntity(InspectionItemRequest request, InspectionLocation location, RiskLevel riskLevel) {
        if (request == null) {
            return null;
        }

        return InspectionItem.builder()
                .inspectionLocation(location)
                .situation(request.situation())
                .description(request.description())
                .riskType(request.riskType())
                .hazardDescription(request.hazardDescription())
                .possibleHarm(request.possibleHarm())
                .probability(request.probability())
                .severity(request.severity())
                .riskLevel(riskLevel)
                .regulatoryStandard(request.regulatoryStandard())
                .nrItem(request.nrItem())
                .correctiveMeasure(request.correctiveMeasure())
                .responsibleName(request.responsibleName())
                .deadline(request.deadline())
                .build();
    }

    public void updateLocationFromRequest(InspectionLocation location, InspectionLocationRequest request, Sector sector) {
        if (location == null || request == null) {
            return;
        }

        location.setSector(sector);
        location.setName(request.name());
        location.setSublocation(request.sublocation());
        location.setEnvironmentDescription(request.environmentDescription());
        location.setActivitiesSummary(request.activitiesSummary());
        location.setExposedJobRoles(request.exposedJobRoles());
        location.setExposedWorkersCount(request.exposedWorkersCount());
        location.setVisitOrder(request.visitOrder());
    }

    public void updateItemFromRequest(InspectionItem item, InspectionItemRequest request, RiskLevel riskLevel) {
        if (item == null || request == null) {
            return;
        }

        item.setSituation(request.situation());
        item.setDescription(request.description());
        item.setRiskType(request.riskType());
        item.setHazardDescription(request.hazardDescription());
        item.setPossibleHarm(request.possibleHarm());
        item.setProbability(request.probability());
        item.setSeverity(request.severity());
        item.setRiskLevel(riskLevel);
        item.setRegulatoryStandard(request.regulatoryStandard());
        item.setNrItem(request.nrItem());
        item.setCorrectiveMeasure(request.correctiveMeasure());
        item.setResponsibleName(request.responsibleName());
        item.setDeadline(request.deadline());
    }
}

