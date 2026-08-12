package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum Severity {

    LOW(1),
    MODERATE(2),
    SERIOUS(3),
    CRITICAL(4);

    private final int value;

    Severity(int value) {
        this.value = value;
    }

}
