package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;

@Getter
public enum RiskLevel {

    LOW("Risco Baixo", "Verde", "Aceitável. Não exige novas ações, apenas manter os controles atuais."),
    MEDIUM("Risco Médio", "Amarelo", "Tolerável. Exige atenção e planejamento de melhorias a médio prazo."),
    HIGH("Risco Alto", "Laranja", "Urgente. Necessita de ações de controle e mitigação em curto prazo."),
    CRITICAL("Risco Crítico", "Vermelho", "Inaceitável. A atividade deve ser interrompida imediatamente até que o risco seja reduzido.");

    private final String label;
    private final String color;
    private final String description;

    RiskLevel(String label, String color, String description) {
        this.label = label;
        this.color = color;
        this.description = description;
    }

    /**
     * Calcula o Nível de Risco a partir do Índice de Risco (IR = Probabilidade x Severidade).
     *
     * @param score Resultado da multiplicação (1 a 25)
     * @return Nível de Risco correspondente
     */
    public static RiskLevel fromScore(int score) {
        if (score <= 4) {
            return LOW;
        }

        if (score <= 9) {
            return MEDIUM;
        }

        if (score <= 16) {
            return HIGH;
        }

        return CRITICAL;
    }
}
