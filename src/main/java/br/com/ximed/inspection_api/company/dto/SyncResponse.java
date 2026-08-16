package br.com.ximed.inspection_api.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resposta de sincronização inicial para o aplicativo móvel contendo os dados brutos")
public record SyncResponse(
        @Schema(description = "Lista de empresas (geralmente apenas 1)")
        List<CompanyResponse> companies,

        @Schema(description = "Lista de todos os estabelecimentos/filiais")
        List<SiteResponse> sites,

        @Schema(description = "Lista de todos os setores")
        List<SectorResponse> sectors
) {}
