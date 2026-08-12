package br.com.ximed.inspection_api.inspection.domain.enums;

public enum RiskLevel {

    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;

    public static RiskLevel fromScore(int score) {

        if (score <= 2) {
            return LOW;
        }

        if (score <= 4) {
            return MEDIUM;
        }

        if (score <= 8) {
            return HIGH;
        }

        return CRITICAL;
    }
}
