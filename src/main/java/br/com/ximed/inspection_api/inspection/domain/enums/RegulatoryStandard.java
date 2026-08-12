package br.com.ximed.inspection_api.inspection.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegulatoryStandard {

    NR_01("NR-01", "Disposições Gerais e Gerenciamento de Riscos Ocupacionais"),
    NR_04("NR-04", "Serviços Especializados em Segurança e em Medicina do Trabalho"),
    NR_05("NR-05", "Comissão Interna de Prevenção de Acidentes e de Assédio"),
    NR_06("NR-06", "Equipamento de Proteção Individual - EPI"),
    NR_07("NR-07", "Programa de Controle Médico de Saúde Ocupacional"),
    NR_09("NR-09", "Avaliação e Controle das Exposições Ocupacionais a Agentes Físicos, Químicos e Biológicos"),
    NR_10("NR-10", "Segurança em Instalações Elétricas e Serviços em Eletricidade"),
    NR_12("NR-12", "Segurança no Trabalho em Máquinas e Equipamentos"),
    NR_17("NR-17", "Ergonomia"),
    NR_35("NR-35", "Trabalho em Altura");

    private final String code;
    private final String title;
}