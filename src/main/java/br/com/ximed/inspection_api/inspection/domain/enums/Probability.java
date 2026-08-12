package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum Probability {

    RARE(1, "Rara"),
    UNLIKELY(2, "Improvável"),
    POSSIBLE(3, "Possível"),
    PROBABLE(4, "Provável"),
    FREQUENT(5, "Frequente");

    private final int value;
    private final String description;

    Probability(int value, String description) {
        this.value = value;
        this.description = description;
    }
}