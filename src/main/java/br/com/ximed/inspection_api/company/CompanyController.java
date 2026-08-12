package br.com.ximed.inspection_api.company;

import br.com.ximed.inspection_api.company.dto.CompanyResponse;
import br.com.ximed.inspection_api.company.dto.SectorResponse;
import br.com.ximed.inspection_api.company.dto.SiteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Tag(
    name = "Company",
    description = "Consulta dos dados da empresa, locais e setores cadastrados"
)
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation (summary = "Consulta os dados da empresa")
    public ResponseEntity<CompanyResponse> getCompany() {
        return ResponseEntity.ok(companyService.getCompany());
    }

    @GetMapping("/sites")
    @Operation (summary = "Consulta as filiais cadastradas")
    public ResponseEntity<List<SiteResponse>> getSites() {
        return ResponseEntity.ok(companyService.getSites());
    }

    @GetMapping("/sites/{siteId}/sectors")
    @Operation (summary = "Consulta os setores cadastrados de uma filial")
    public ResponseEntity<List<SectorResponse>> getSectors(@PathVariable UUID siteId) {
        return ResponseEntity.ok(companyService.getSectors(siteId));
    }
}