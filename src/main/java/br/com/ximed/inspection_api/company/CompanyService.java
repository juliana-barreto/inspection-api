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

    // Método temporário para obter a empresa atual, considerando que há apenas uma empresa cadastrada.
    private Company getCurrentCompany() {
        return companyRepository.findFirstBy()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empresa não cadastrada")
                );
    }

    @Transactional(readOnly = true)
    public br.com.ximed.inspection_api.company.dto.SyncResponse getSyncData() {
        Company company = getCurrentCompany();
        
        List<CompanyResponse> companies = List.of(new CompanyResponse(
                company.getId(),
                company.getCorporateName(),
                company.getTradeName()
        ));
        
        List<SiteResponse> sites = company.getSites().stream()
                .map(site -> new SiteResponse(
                        site.getId(),
                        company.getId(),
                        site.getName(),
                        site.getCnpj(),
                        site.getCnae(),
                        site.getAddress()
                ))
                .toList();
                
        List<SectorResponse> sectors = company.getSites().stream()
                .flatMap(site -> site.getSectors().stream()
                        .map(sector -> new SectorResponse(
                                sector.getId(),
                                site.getId(),
                                sector.getName()
                        ))
                )
                .toList();
                
        return new br.com.ximed.inspection_api.company.dto.SyncResponse(
                companies,
                sites,
                sectors
        );
    }
}