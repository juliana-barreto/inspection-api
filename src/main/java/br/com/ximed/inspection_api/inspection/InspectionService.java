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
import org.springframework.web.multipart.MultipartFile;

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
        inspection.setCode(generateNextCode());
        inspection.setStatus(InspectionStatus.DRAFT);
        inspectionRepository.save(inspection);

        return inspectionMapper.toResponse(inspection);
    }

    @Transactional(readOnly = true)
    public List<InspectionSummaryResponse> findAll(UUID siteId, InspectionStatus status) {
        List<Inspection> inspections = inspectionRepository.findAllBySiteAndStatus(siteId, status);
        return inspections.stream()
                .map(inspectionMapper::toSummaryResponse)
                .toList();
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
                
        if (inspection.getStatus() != InspectionStatus.DRAFT) {
            throw new BusinessException("Apenas inspeções em rascunho podem ser excluídas.");
        }
        
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
    public void deleteLocation(UUID inspectionId, UUID locationId) {
        InspectionLocation location = inspectionLocationRepository.findByIdAndInspectionId(locationId, inspectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada"));
                
        if (location.getInspection().getStatus() != InspectionStatus.DRAFT) {
            throw new BusinessException("Apenas locais de inspeções em rascunho podem ser excluídos.");
        }
        
        inspectionLocationRepository.delete(location);
    }

    @Transactional
    public void deleteItem(UUID inspectionId, UUID locationId, UUID itemId) {
        InspectionItem item = inspectionItemRepository.findByIdAndLocationIdAndInspectionId(itemId, locationId, inspectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
                
        if (item.getInspectionLocation().getInspection().getStatus() != InspectionStatus.DRAFT) {
            throw new BusinessException("Apenas itens de inspeções em rascunho podem ser excluídos.");
        }
        
        inspectionItemRepository.delete(item);
    }

    @Transactional
    public InspectionLocationResponse updateLocation(UUID inspectionId, UUID locationId, InspectionLocationRequest request) {
        java.util.Optional<InspectionLocation> optLocation = inspectionLocationRepository.findByIdAndInspectionId(locationId, inspectionId);
                
        Sector sector = sectorRepository.findById(request.sectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
                
        if (optLocation.isPresent()) {
            InspectionLocation location = optLocation.get();
            inspectionMapper.updateLocationFromRequest(location, request, sector);
            inspectionLocationRepository.save(location);
            return inspectionMapper.toLocationResponse(location);
        } else {
            Inspection inspection = inspectionRepository.findById(inspectionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));
            InspectionLocation location = inspectionMapper.toLocationEntity(request, inspection, sector);
            location.setId(locationId);
            inspectionLocationRepository.save(location);
            return inspectionMapper.toLocationResponse(location);
        }
    }

    @Transactional
    public InspectionItemResponse updateItem(UUID inspectionId, UUID locationId, UUID itemId, InspectionItemRequest request) {
        java.util.Optional<InspectionItem> optItem = inspectionItemRepository.findByIdAndLocationIdAndInspectionId(itemId, locationId, inspectionId);
        
        RiskLevel riskLevel = calculateRiskLevel(request.probability(), request.severity());
        
        if (optItem.isPresent()) {
            InspectionItem item = optItem.get();
            inspectionMapper.updateItemFromRequest(item, request, riskLevel);
            inspectionItemRepository.save(item);
            return inspectionMapper.toItemResponse(item);
        } else {
            InspectionLocation location = inspectionLocationRepository.findByIdAndInspectionId(locationId, inspectionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada"));
            InspectionItem item = inspectionMapper.toItemEntity(request, location, riskLevel);
            item.setId(itemId);
            inspectionItemRepository.save(item);
            return inspectionMapper.toItemResponse(item);
        }
    }



    @Transactional
    public InspectionResponse submitForApproval(UUID id) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));

        inspection.setStatus(InspectionStatus.WAITING_APPROVAL);
        inspectionRepository.save(inspection);

        return inspectionMapper.toResponse(inspection);
    }




    @Transactional(readOnly = true)
    public List<PreviousNonConformityResponse> getPreviousNonConformities(UUID siteId) {
        List<InspectionItem> items = inspectionItemRepository.findPreviousNonConformitiesBySiteId(siteId);
        return items.stream()
                .map(inspectionMapper::toPreviousNonConformityResponse)
                .toList();
    }

    private String generateNextCode() {
        int year = java.time.Year.now().getValue();
        long nextSeq = inspectionRepository.count() + 1;
        return String.format("INS-%d-%03d", year, nextSeq);
    }

    private RiskLevel calculateRiskLevel(Probability probability, Severity severity) {
        if (probability == null || severity == null) {
            return null;
        }
        int score = probability.getValue() * severity.getValue();
        return RiskLevel.fromScore(score);
    }
}