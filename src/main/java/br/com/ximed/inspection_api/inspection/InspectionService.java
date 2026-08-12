package br.com.ximed.inspection_api.inspection;

import br.com.ximed.inspection_api.company.Sector;
import br.com.ximed.inspection_api.company.SectorRepository;
import br.com.ximed.inspection_api.company.Site;
import br.com.ximed.inspection_api.company.SiteRepository;
import br.com.ximed.inspection_api.exception.BusinessException;
import br.com.ximed.inspection_api.exception.ResourceNotFoundException;
import br.com.ximed.inspection_api.inspection.domain.*;
import br.com.ximed.inspection_api.inspection.domain.enums.*;
import br.com.ximed.inspection_api.inspection.dto.*;
import br.com.ximed.inspection_api.inspection.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final InspectionLocationRepository inspectionLocationRepository;
    private final InspectionItemRepository inspectionItemRepository;

    private final SiteRepository siteRepository;
    private final SectorRepository sectorRepository;

    private final InspectionMapper inspectionMapper;

    @Transactional
    public InspectionResponse create(InspectionRequest request) {

        Site site = siteRepository.findById(request.siteId())
                .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado"));

        Inspection inspection = inspectionMapper.toEntity(request, site);
        inspectionRepository.save(inspection);

        return inspectionMapper.toResponse(inspection);
    }

    @Transactional(readOnly = true)
    public InspectionResponse findById(UUID id) {
        Inspection inspection = inspectionRepository.findByIdWithReportData(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));
        return inspectionMapper.toResponse(inspection);
    }

    @Transactional
    public InspectionResponse update(UUID id, InspectionRequest request) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));

        inspectionMapper.updateEntityFromRequest(inspection, request);
        inspectionRepository.save(inspection);
        return inspectionMapper.toResponse(inspection);
    }

    @Transactional
    public void delete(UUID id) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));
        inspectionRepository.delete(inspection);
    }

    @Transactional
    public InspectionLocationResponse addLocation(UUID inspectionId, InspectionLocationRequest request) {
        Inspection inspection = inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));

        Sector sector = sectorRepository.findById(request.sectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));

        if (!sector.getSite().getId().equals(inspection.getSite().getId())) {
            throw new BusinessException("O setor não pertence ao local da inspeção");
        }

        InspectionLocation location = inspectionMapper.toLocationEntity(request, inspection, sector);
        inspectionLocationRepository.save(location);
        return inspectionMapper.toLocationResponse(location);
    }

    @Transactional
    public InspectionItemResponse addItem(UUID inspectionId, UUID locationId, InspectionItemRequest request) {
        InspectionLocation location = inspectionLocationRepository.findByIdAndInspectionId(locationId, inspectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Localização de inspeção não encontrada"));

        RiskLevel riskLevel = calculateRiskLevel(request.probability(), request.severity());

        InspectionItem item = inspectionMapper.toItemEntity(request, location, riskLevel);
        inspectionItemRepository.save(item);
        return inspectionMapper.toItemResponse(item);
    }

    @Transactional
    public InspectionResponse submitForApproval(UUID id) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));

        inspection.setStatus(InspectionStatus.WAITING_APPROVAL);
        inspectionRepository.save(inspection);

        return inspectionMapper.toResponse(inspection);
    }

    private RiskLevel calculateRiskLevel(Probability probability, Severity severity) {
        if (probability == null || severity == null) {
            return null;
        }
        int score = probability.getValue() * severity.getValue();
        return RiskLevel.fromScore(score);
    }

}