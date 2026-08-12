package br.com.ximed.inspection_api.company.dto;

import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String corporateName,
        String tradeName
) {}