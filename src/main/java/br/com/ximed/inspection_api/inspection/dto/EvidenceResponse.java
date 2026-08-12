package br.com.ximed.inspection_api.inspection.dto;

import java.util.UUID;

public record EvidenceResponse(
        UUID id,
        String imgUrl,
        String caption
) {
}