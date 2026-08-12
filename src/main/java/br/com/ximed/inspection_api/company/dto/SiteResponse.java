package br.com.ximed.inspection_api.company.dto;

import java.util.UUID;

public record SiteResponse(
        UUID id,
        String name,
        String cnpj,
        String cnae,
        String address
) {}
