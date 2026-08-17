package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum InspectionSituation {
    CONFORMING("CONFORME", "#28a745"),
    NON_CONFORMING("NÃO CONFORME", "#dc3545"),
    NOT_APPLICABLE("NÃO APLICÁVEL", "#6c757d");

    private final String label;
    private final String color;

    InspectionSituation(String label, String color) {
        this.label = label;
        this.color = color;
    }
}