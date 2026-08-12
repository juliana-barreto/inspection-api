package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum Severity {

    INSIGNIFICANT(1, "Insignificante"),
    MINOR(2, "Menor"),
    MODERATE(3, "Moderada"),
    MAJOR(4, "Maior"),
    CATASTROPHIC(5, "Catastrófica");

    private final int value;
    private final String description;

    Severity(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
