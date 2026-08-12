package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum Probability {

    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    Probability(int value) {
        this.value = value;
    }
}