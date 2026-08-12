package br.com.ximed.inspection_api.inspection.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados da evidência fotográfica anexada a um item inspecionado")
public record EvidenceResponse(
        @Schema(description = "ID único da evidência")
        UUID id,

        @Schema(description = "URL para acesso/visualização da imagem", example = "https://storage.ximed.com.br/evidences/foto_posto_trabalho.jpg")
        String imgUrl,

        @Schema(description = "Legenda ou descrição da fotografia", example = "Monitor posicionado abaixo da altura adequada e cadeira sem ajuste suficiente para suporte postural.")
        String caption
) {
}