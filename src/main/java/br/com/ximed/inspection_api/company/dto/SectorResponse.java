package br.com.ximed.inspection_api.company.dto;

import java.util.UUID;

public record SectorResponse(
        UUID id,
        String name,
        String description
) {}
