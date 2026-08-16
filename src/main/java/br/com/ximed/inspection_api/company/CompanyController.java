package br.com.ximed.inspection_api.company;

import br.com.ximed.inspection_api.company.dto.CompanyResponse;
import br.com.ximed.inspection_api.company.dto.SectorResponse;
import br.com.ximed.inspection_api.company.dto.SiteLocationResponse;
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

    @GetMapping("/sync")
    @Operation (summary = "Sincronização inicial: Consulta todos os dados da empresa (Company, Sites, Sectors) em formato flat")
    public ResponseEntity<br.com.ximed.inspection_api.company.dto.SyncResponse> getSyncData() {
        return ResponseEntity.ok(companyService.getSyncData());
    }
}