package br.com.ximed.inspection_api.company;

import br.com.ximed.inspection_api.company.dto.CompanyResponse;
import br.com.ximed.inspection_api.company.dto.SectorResponse;
import br.com.ximed.inspection_api.company.dto.SiteResponse;
import br.com.ximed.inspection_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SiteRepository siteRepository;
    private final SectorRepository sectorRepository;

    @Transactional(readOnly = true)
    public CompanyResponse getCompany() {
        Company company = getCurrentCompany();

        return new CompanyResponse(
                company.getId(),
                company.getCorporateName(),
                company.getTradeName()
        );
    }

    @Transactional(readOnly = true)
    public List<SiteResponse> getSites() {
        Company company = getCurrentCompany();

        return siteRepository.findByCompanyId(company.getId())
                .stream()
                .map(site -> new SiteResponse(
                        site.getId(),
                        site.getName(),
                        site.getCnpj(),
                        site.getCnae(),
                        site.getAddress()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SectorResponse> getSectors(UUID siteId) {
        Company company = getCurrentCompany();

        Site site = (Site) siteRepository
                .findByIdAndCompanyId(siteId, company.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Local não encontrado")
                );

        return sectorRepository.findBySiteId(site.getId())
                .stream()
                .map(sector -> new SectorResponse(
                        sector.getId(),
                        sector.getName(),
                        sector.getDescription()
                ))
                .toList();
    }

    // Método temporário para obter a empresa atual, considerando que há apenas uma empresa cadastrada.
    private Company getCurrentCompany() {
        return companyRepository.findFirstBy()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empresa não cadastrada")
                );
    }
}