package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum RiskType {
    PHYSICAL("Físico"),
    CHEMICAL("Químico"),
    BIOLOGICAL("Biológico"),
    ERGONOMIC("Ergonômico"),
    ACCIDENT("Acidente");

    private final String label;

    RiskType(String label) {
        this.label = label;
    }
}